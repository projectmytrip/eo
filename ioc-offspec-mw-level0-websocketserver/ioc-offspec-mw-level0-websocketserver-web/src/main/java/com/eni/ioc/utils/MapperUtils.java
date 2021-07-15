package com.eni.ioc.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.eni.ioc.pojo.client.response.ClientResponsePojo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtils {


	
	private static final Logger logger = LoggerFactory.getLogger(MapperUtils.class);

	private MapperUtils() {
	}

	public static <T> T convertJsonToPojo(String json, Class<T> clazz) throws IOException {

		T pojo = null;
		
		//logger.info(json);

		ObjectMapper mapper = new ObjectMapper();
		pojo = mapper.readValue(json, clazz);

		return pojo;
	}
	
	public static String removeLastChar(String s) {
	    return (s == null || s.length() == 0)
	      ? null
	      : (s.substring(0, s.length() - 1));
	}
	
	
	
	public static String makeFinalPayload(ArrayList<String> userLevels, Class responseClass, Field level, String event, 
			ClientResponsePojo response) {
		
		
		JSONArray value = null;
		String finalPayload = "";
		for(String levelCode : userLevels) {
			levelCode = levelCode.toLowerCase();
			
			try {
				level = responseClass.getDeclaredField(levelCode);
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
			
			level.setAccessible(true);
			
			try {
				value = (JSONArray) level.get(response);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
			
			if(value != null) {
				finalPayload += "\""+levelCode+"\":"+value+",";
			}
		}
		
		finalPayload = "{\"data\":{"+finalPayload;
		finalPayload = MapperUtils.removeLastChar(finalPayload)+"},"
				+"\"event\":\""
				+event+"\"}";
		
		return finalPayload;
	}

}
