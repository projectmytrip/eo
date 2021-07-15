package com.eni.ioc.pi.client.rest;

import com.eni.ioc.common.ItemDeserializer;
import com.eni.ioc.pi.rest.response.HistoryResponse;
import com.eni.ioc.pi.rest.response.Item;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.request.DetailElement;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PiClientRest {
		
	private static final Logger logger = LoggerFactory.getLogger(PiClientRest.class);
	      
        private static final int HISTORYKPIHOURS = 48;
        private static final int HISTORYSULFURDAYS = 30;
        private static final int HISTORYH2SHOURS = 72;
    
	public static void  sendGetHistory() {
		
		HashMap<String, HttpGet> mapHttpGet = inizializeUrlRequestHistory();  
		Map<String, ArrayList<DetailElement>> mapToStore = new HashMap<>();
		logger.info("History REST : Sych Map  ---- IN " );
			
		for (Map.Entry<String, HttpGet> entry: mapHttpGet.entrySet()){

			String key = entry.getKey();
			HttpResponse response = doCall(entry);

			int statusCode = null != response ? response.getStatusLine().getStatusCode() : -1;
			if (null != response && statusCode == 200) {
					String messageResponsePiRestGetHistory = parseResponse(response);
					
					ObjectMapper mapper = prepareMapper();
														
					try {
						HistoryResponse piHistoryResponse = mapper.readValue(messageResponsePiRestGetHistory, HistoryResponse.class);
						
						ArrayList<DetailElement> detailElementList = mapToStore.get(key);
						if (detailElementList == null) {
							detailElementList = new ArrayList<>();
						}
						for (Item item : piHistoryResponse.getItems()) {
							detailElementList.add(convertItemToDetailElement(item));
						}
						Collections.reverse(detailElementList);
						mapToStore.put(key, detailElementList);
						
					} catch (JsonParseException e) {
						logger.error("JsonParseException", e);
					} catch (JsonMappingException e) {
						logger.error("JsonMappingException", e);
					} catch (IOException e) {
						logger.error("IOException", e);
					}
			
			}else {
				logger.error("PROBLEMA CON LA CHIAMATA DELLO STORICO !!!");
			}
		}
		
		StoreDataServiceRequest req = StoreDataServiceClientRest.createRequest("COVA",false,true,true, mapToStore);
		String resp = StoreDataServiceClientRest.postStoreData(ApplicationProperties.getPersistenceUrl(),req);
		logger.info(resp);
		logger.info("History REST : Sych Map  ---- OUT " );
				
	}
        
        private static HashMap<String,HttpGet> inizializeUrlRequestHistory() {
                HashMap<String,HttpGet> urlRequestHistory =new HashMap<String,HttpGet> ();
                
		String interval30s= "&interval=30s";
		String interval1d= "";
		String endTime="&endtime=";
		String recorded = "/recorded?starttime=";
                String maxCount72h = "&maxCount=8640";
                String maxCount48h = "&maxCount=5760";

                long timezoneDifferenceSeconds = getTimeZoneDiff();
                //Nonostante il sistema risponda in UTC prende in input le date ocn timezone +2
		LocalDateTime localDateTimeEnd = LocalDateTime.now().plusSeconds(timezoneDifferenceSeconds);

		
		String endTimeDate =localDateTimeEnd.truncatedTo(ChronoUnit.MINUTES).toString();
		logger.debug("endTime: {}", endTimeDate);		
		LocalDateTime localDateStartTime =localDateTimeEnd.truncatedTo(ChronoUnit.MINUTES).minusHours(HISTORYKPIHOURS);
		String startTime=localDateStartTime.toString();
		logger.debug("starttime: {}", startTime);	
		
		String startTime30gg=localDateTimeEnd.truncatedTo(ChronoUnit.MINUTES).minusDays(HISTORYSULFURDAYS).toString();

		/* H2S_Concentration requires a longer range (72 hours)*/
		LocalDateTime localDateTimeEndH2S = LocalDateTime.now().plusSeconds(timezoneDifferenceSeconds);

		String endTimeH2S =localDateTimeEndH2S.truncatedTo(ChronoUnit.MINUTES).toString();
		logger.debug("start time H2S {}", endTimeH2S);		
		LocalDateTime localDateTimeEnd2 =localDateTimeEnd.truncatedTo(ChronoUnit.MINUTES).minusHours(HISTORYH2SHOURS);
		String startTimeH2S=localDateTimeEnd2.toString();
		logger.debug("start time H2S {}", startTimeH2S);
		
		//CO2_ActualValue
		urlRequestHistory.put("CO2_ActualValue",new HttpGet(ApplicationProperties.getUrlCO2()+recorded+startTime+endTime+endTimeDate+interval30s+maxCount48h));
		// DWP_H2O
		urlRequestHistory.put("DWP_H2O",new HttpGet(ApplicationProperties.getUrlDWPH2O()+recorded+startTime+endTime+endTimeDate+interval30s+maxCount48h));
		//DWP_HC
		urlRequestHistory.put("DWP_HC",new HttpGet(ApplicationProperties.getUrlDWPHC()+recorded+startTime+endTime+endTimeDate+interval30s+maxCount48h));
		//H2S_Concentration
		urlRequestHistory.put("H2S_Concentration",new HttpGet(ApplicationProperties.getUrlH2SConcentration()+recorded+startTimeH2S+endTime+endTimeH2S+interval30s+maxCount72h));
		//Sulfur_HRC  !!! N.B. diversa frequenza di campionatura 
		urlRequestHistory.put("Sulfur_HRC",new HttpGet(ApplicationProperties.getUrlSulfurHRC()+recorded+startTime30gg+endTime+endTimeDate+interval1d));
		//Sulfur_Total  !!! N.B. diversa frequenza di campionatura 
		urlRequestHistory.put("Sulfur_Total",new HttpGet(ApplicationProperties.getUrlSulfurTotal()+recorded+endTimeDate+endTime+startTime30gg+interval1d));
		//WOBBE_ActualValue
		urlRequestHistory.put("WOBBE_ActualValue",new HttpGet(ApplicationProperties.getUrlWobbeActual()+recorded+startTime+endTime+endTimeDate+interval30s+maxCount48h));
		//NEW UNKNOWN KEYNAME
		urlRequestHistory.put("COS_ActualValue",new HttpGet(ApplicationProperties.getUrlCOSActual()+recorded+startTime+endTime+endTimeDate+interval30s+maxCount48h));
                
                return urlRequestHistory;
	}
        
        private static long getTimeZoneDiff(){
		TimeZone tzApp = TimeZone.getTimeZone("Europe/Rome");
		TimeZone tzPI = TimeZone.getTimeZone("UTC");
		return (tzApp.getRawOffset() - tzPI.getRawOffset() + tzApp.getDSTSavings()
				- tzPI.getDSTSavings()) / 1000;
	}
	
	private static HttpResponse doCall(Map.Entry<String, HttpGet> entry){
        HttpGet request = entry.getValue();
        String key = entry.getKey();
        logger.info("Get per l'elemento: "+key+" ; url: "+request.getURI());
		
        String auth = ApplicationProperties.getPiUsername()+":"+ApplicationProperties.getPiPassword();
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return response;
	}
	
	private static String parseResponse(HttpResponse response) {
		BufferedReader rd = null;
		StringBuilder result = new StringBuilder();
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			result = new StringBuilder();
			String line = "";

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (UnsupportedOperationException | IOException e) {
			logger.error("Error during parse response", e);
		}
		return result.toString();
	}
	
	private static ObjectMapper prepareMapper(){
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Item.class, new ItemDeserializer());
		mapper.registerModule(module);
		return mapper;
	}
	
	private static DetailElement convertItemToDetailElement(Item item){
		DetailElement detail = new DetailElement();
		detail.setTimestamp(item.getTimestamp());
		detail.setValue(item.getValue());
		detail.setUnitsAbbreviation(item.getUnitsAbbreviation());
		detail.setValidData(item.isGood());
		return detail;
	}
}
