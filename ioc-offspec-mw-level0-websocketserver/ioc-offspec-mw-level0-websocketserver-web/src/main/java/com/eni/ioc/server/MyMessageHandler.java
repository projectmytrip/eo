package com.eni.ioc.server;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.eni.ioc.ApplicationMain;
import com.eni.ioc.common.DetailSession;
import com.eni.ioc.pojo.client.response.ClientResponsePojo;
import com.eni.ioc.pojo.profile.Domain;
import com.eni.ioc.pojo.profile.Level;
import com.eni.ioc.pojo.profile.ProfilePojo;
import com.eni.ioc.service.OffSpecService;
import com.eni.ioc.utils.Esito;
import com.eni.ioc.utils.JWTUtils;
import com.eni.ioc.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class MyMessageHandler extends TextWebSocketHandler {
	@Autowired
	OffSpecService offSpecService;
	

	@Autowired
	private Environment env;

	private static final Logger logger = LoggerFactory.getLogger(MyMessageHandler.class);
	private static final String prodOpt = "PRODOPT";

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        
    	synchronized (ApplicationMain.mapAssetSession) {
    		logger.info("connection closing: "+session.getId());
    		
    		Boolean sessionFound = false;
    		try {
	    		for (Map.Entry<String, ArrayList<DetailSession>> mapAssetSession : ApplicationMain.mapAssetSession.entrySet()) {
	    		    String asset = mapAssetSession.getKey();
	    		    ArrayList<DetailSession> sessions = mapAssetSession.getValue();
	    		        			    			
	    		    for (DetailSession checkds : sessions) {
	    		    	if(checkds.getSession().getId().equals(session.getId())){
	        				sessions.remove(checkds);
	        				sessionFound = true;
	        				logger.info("Websocket session deleted after connection closed: "+checkds.getUID()+" - "+session.getId());
	            		    break;
	            		}
	    		    }
	    		    
	    		    if(sessionFound) {
	    		    	ApplicationMain.mapAssetSession.put(asset, sessions);
	    		    	break;
	    		    }
	    				
	    		}
    		} catch (Exception e) {
    			logger.error("Errore nell'eliminazione di una sessione: "+e.getMessage()); 
    		}
		}   	   	
    }
    
    
    public static void forceConnectionClose(WebSocketSession session) {
        
    	synchronized (ApplicationMain.mapAssetSession) {
    		logger.info("forced connection closing: "+session.getId());
    		
    		Boolean sessionFound = false;
    		try {
	    		for (Map.Entry<String, ArrayList<DetailSession>> mapAssetSession : ApplicationMain.mapAssetSession.entrySet()) {
	    		    String asset = mapAssetSession.getKey();
	    		    ArrayList<DetailSession> sessions = mapAssetSession.getValue();
	    		        			    			
	    		    for (DetailSession checkds : sessions) {
	    		    	if(checkds.getSession().getId().equals(session.getId())){
	        				sessions.remove(checkds);
	        				sessionFound = true;
	        				logger.info("Websocket session deleted after connection closed: "+checkds.getUID()+" - "+session.getId());
	            		    break;
	            		}
	    		    }
	    		    
	    		    if(sessionFound) {
	    		    	ApplicationMain.mapAssetSession.put(asset, sessions);
	    		    	break;
	    		    }
	    				
	    		}
    		} catch (Exception e) {
    			logger.error("Errore nell'eliminazione di una sessione: "+e.getMessage()); 
    		}
		}   	   	
    }
 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("Websocket session created after connection opened: "+session.getId());
    	session.sendMessage(new TextMessage("{\"data\":\"iscovered server. This is the first message from the Server. User send method to save session.\"}"));
    }
    
 
    
    
    /* 
     * mando evento e dati al FE
     * 
     */
    public static void sendMessageToAsset(String asset, String event, String keyname, Esito body) {
    	synchronized (ApplicationMain.mapAssetSession) {
    		
	    	String jsonResult = null;
	    	ClientResponsePojo response = null;
			Field level = null;
			Class responseClass = null;
			Boolean jsonResultHasLevels = false; 
			
			try {
				
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				jsonResult = ow.writeValueAsString(body.getData());
				response = MapperUtils.<ClientResponsePojo>convertJsonToPojo(jsonResult, ClientResponsePojo.class);
				responseClass = response.getClass();
	
				jsonResultHasLevels = true;
				
				if(event.equals(prodOpt)){
				     jsonResultHasLevels = false;
				}
				
			} catch (IOException e) {
				logger.warn("La coversione del payload in POJO è andata in errore, probabilmente il payload non ha LEVELS");
				jsonResultHasLevels = false; 
			}
    			
			try {
				ArrayList<DetailSession> sessions = ApplicationMain.mapAssetSession.get(asset);
				
				for(DetailSession session : sessions) {
					try {
						String finalPayload = "";

						if(jsonResultHasLevels) {
    						Map<String, ArrayList<String>> userLevelsMap = session.getLevels();	    						
    						ArrayList<String> userLevels = userLevelsMap.get(event);
							finalPayload = MapperUtils.makeFinalPayload(userLevels, responseClass, level, event, response);							
						} else {							
							finalPayload = "{\"data\":"+jsonResult+",\"event\":\""+event+"\"}";
						}
						
						logger.info("Sending message to Websocket session: "+session.getUID()+" - "+session.getSession().getId());
						session.getSession().sendMessage(new TextMessage(finalPayload));
					} catch (IllegalStateException e) {
						logger.error("Message sending EXCEPTION: "+e.getMessage());
						session.getSession().close();
						forceConnectionClose(session.getSession());
					}
				}
			
			} catch (Exception e) {
				logger.error("Message sending EXCEPTION: Non ci sono utenti connessi all'asset");
				//e.printStackTrace();
			}
			
		}
    }
    
    
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
    	
    	/* GESTISCO I PING-PONG */
		try {
			if (textMessage.getPayload().equals("ping")) {
				session.sendMessage(new TextMessage("pong"));
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	
    	
    	/*
    	 * dopo aver stabilito la connessione bisongna chiamare il metodo send (con i dati dell'uid)
    	 * salvare l'utente e la sua sessione nella mappa asset-utenti
    	 */
		
    	synchronized (ApplicationMain.mapAssetSession) {

    	    		
    		try {
    	       			
	    		JSONParser jsonParser = new JSONParser();
	    		JSONObject jo = (JSONObject) jsonParser.parse(textMessage.getPayload());
    		
	    		ArrayList<DetailSession> sessions;
	    		String asset = (String) jo.get("asset");
	    		String uid = (String) jo.get("uid");
	    		String jwt = (String) jo.get("jwt");
	    		jwt.replaceAll("\\n", "").replaceAll("\\r", "").trim();
	    		logger.info(jwt);
	    		Boolean jwtCheck = JWTUtils.checkJWT(jwt);
	    		
	    		if(jwtCheck) {

		    		if(ApplicationMain.mapAssetSession.get(asset) != null) {	    			
		    			sessions = ApplicationMain.mapAssetSession.get(asset);	    			
		    		} else {	    			
		    			sessions = new ArrayList<>();
		    		}
		    		
					logger.info("Session creation after user first message: "+session.getId()+", in asset "+asset);
					
					// TODO Auto-generated method stub
					RestTemplate restTemplate = new RestTemplate();
					
					HttpHeaders headers = new HttpHeaders();
					HttpEntity<String> entity = new HttpEntity<String>("Call for broadcast!", headers);
					headers.setContentType(MediaType.APPLICATION_JSON);
					
					String apiToCall = env.getProperty("api.profile")+""+asset+"?uid={uid}";
	
					ResponseEntity<Esito> resultEntity = restTemplate.exchange(apiToCall, HttpMethod.GET, entity, Esito.class, uid);
					ProfilePojo profile = null;
					if (resultEntity != null) {
						if (resultEntity.getStatusCodeValue() == 200) {
							try {
								Object r = resultEntity.getBody().getData();
						    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
						    	String jsonResult = ow.writeValueAsString(r);
						    	//logger.info(jsonResult);
						    	profile = MapperUtils.<ProfilePojo>convertJsonToPojo(jsonResult, ProfilePojo.class);
							} catch(Exception e) {
								logger.error("Errore nella conversione del profilo da JSON to POJO: "+e.getStackTrace());
							}
						} else {
	
						}
					} else {
	
					}
					
					Map<String, ArrayList<String>> mapDomainLevels = new HashMap<String, ArrayList<String>>();
					Domain[] domains = profile.getDomains();
					
					for(Domain domain : domains) {
						String domainCode = domain.getCode();
						Level[] levels = domain.getLevels();
						ArrayList<String> cards = new ArrayList<String>();
	
						for(Level level : levels) {
							String levelCode = level.getCode();
							cards.add(levelCode);
						}
						mapDomainLevels.put(domainCode, cards);
					}
					
	    			DetailSession ds = new DetailSession();
	    			ds.setSession(session);
	    			ds.setUID(uid);
	    			ds.setLevels(mapDomainLevels);
	    			sessions.add(ds);
	    			    		
	    			ApplicationMain.mapAssetSession.put(asset, sessions);
	    		} else {
	    			
	    			try {
						session.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("Errore nella chiusura connessione post JWT non valido: "+e.getMessage());
					}
	    			
	    		}
	    		
    		} catch(ParseException pe) {
    			try {
					session.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Errore nella chiusura connessione post JWT non valido: "+e.getMessage());
				}
				logger.error("Websocket session creation failed - EXCEPTION: "+pe.getMessage());    	        
    		} catch(Exception pe) {
    			try {
					session.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Errore nella chiusura connessione post JWT non valido: "+e.getMessage());
				}
				logger.error("Websocket session creation failed - EXCEPTION: "+pe.getMessage());    	        
    		}
    		
		}
    }
}