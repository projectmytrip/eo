package com.eni.ioc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.eni.ioc.common.Response;
import com.eni.ioc.common.ResultResponse;
import com.eni.ioc.common.WebSocketServerRequest;
import com.eni.ioc.server.MyMessageHandler;
import com.eni.ioc.utils.Esito;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


@RestController
@RequestMapping("/api")
public class WebsocketServerController {

	@Autowired
	private Environment env;
	 
	private static final Logger logger = LoggerFactory.getLogger(WebsocketServerController.class);



	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		logger.info(" -- Controller ping Called -- ");
		return "Architettura microservizi - Pong OK";
	}

	
	@RequestMapping(value = "/checkJWT", method = RequestMethod.POST)
	public @ResponseBody Response<?> checkJWT(@RequestBody(required = false) String input,
			HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		ResultResponse result = null;
		
		//logger.info(input);
        String publicKeyContent = null;
		try {
			File file = ResourceUtils.getFile("classpath:pubkeysvil.pem");
			//publicKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("public_key.pem").toURI())));
		      publicKeyContent = new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 

        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replaceAll("\\r", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

		try { 
			Jwts.parser().setSigningKey(pubKey).parseClaimsJws(input);
			logger.info("token valido");
			result = new ResultResponse(200, "valido");
		} catch (ExpiredJwtException ex) {
			logger.info("token non valido - SCADUTO");
			result = new ResultResponse(200, "scaduto", ex.getMessage());
		} catch (InvalidClaimException ex) {
			logger.info("token non valido");
			result = new ResultResponse(200, "non valido", ex.getMessage());
		} catch (JwtException ex) {
			logger.info("token non valido - generic");
			result = new ResultResponse(200, "non valido", ex.getMessage());
		}
		return buildResponse(result, "", "", "");
	}	
	
	
	
	@RequestMapping(value = "/brodcastChanges", method = RequestMethod.POST)
	public @ResponseBody Response<?> broadcastChanges(@RequestBody(required = false) String input,
			HttpServletRequest request) {
		ResultResponse result = null;
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			WebSocketServerRequest requestWebsocketClient = mapper.readValue(input, WebSocketServerRequest.class);
			
			String asset = requestWebsocketClient.getAsset();
			String event = requestWebsocketClient.getEvent();
			String keyname = requestWebsocketClient.getKeyname();
			
			RestTemplate restTemplate = new RestTemplate();
						
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer "+env.getProperty("api.jwt"));
			
			HttpEntity<String> entity = new HttpEntity<String>("Call for broadcast!", headers);
			
			String apiToCall = env.getProperty("api."+event);
			
			if(keyname != null && keyname.length() > 0) {
				apiToCall += keyname;
			}
			
			logger.info("["+request.getSession().getId()+"] broadcast value for asset : "+asset+""
					+ ", for event: "+event+""
					+ ", for keyname: "+keyname+""
					+ ", by uri: "+apiToCall);
			
			ResponseEntity<Esito> resultEntity = restTemplate.exchange(apiToCall, HttpMethod.GET, entity, Esito.class);
			
			if (resultEntity != null) {
				if (resultEntity.getStatusCodeValue() == 200) {
					logger.info("Chiamata REST a "+apiToCall+" - "+resultEntity.getStatusCodeValue());
					//logger.info(resultEntity.getBody().toString());
					result = new ResultResponse(200, "OK");
					MyMessageHandler.sendMessageToAsset(asset, event, keyname, resultEntity.getBody());
					return buildResponse(result, resultEntity.getBody(), event,  keyname);
				} else {
					result = new ResultResponse(501, "KO", "Impossibile propagare il dettaglio di errore 1");
					logger.error("["+request.getSession().getId()+"] broadcasting ERROR: "+resultEntity.getStatusCodeValue()+" - "+resultEntity.getBody());
					return buildResponse(result, "", event, "");
				}
			} else {
				result = new ResultResponse(501, "KO", "Impossibile propagare il dettaglio di errore 2");
				logger.error("["+request.getSession().getId()+"] broadcasting ERROR: 501 - ResultEntity null");
				return buildResponse(result, "", event, "");
			}
			
			
		} catch (Exception e) {
			result = new ResultResponse(501, "KO", e.getMessage());
			logger.error("["+request.getSession().getId()+"] broadcasting EXCEPTION: "+e.toString());
			return buildResponse(result, "", "", "");
		}

	}
	
	/**
	 * Customizzare la response
	 * 
	 * @param result
	 * @param data
	 * @return
	 */
	private <T> Response<T> buildResponse(ResultResponse result, T data, String event, String keyname) {
		Response<T> response = new Response<T>();
		if (result == null) {
			ResultResponse myResult = new ResultResponse();
			myResult.setCode(HttpStatus.OK.value());
			myResult.setMessage(HttpStatus.OK.getReasonPhrase());
			response.setResult(myResult);
		} else {
			response.setResult(result);
		}
		response.setData(data);
		response.setEvent(event);
		response.setKeyname(keyname);
		return response;
	}
	
	
	
	
}
