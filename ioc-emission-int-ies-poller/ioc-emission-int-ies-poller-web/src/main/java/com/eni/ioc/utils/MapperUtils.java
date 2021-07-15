package com.eni.ioc.utils;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.eni.ioc.pojo.thresholds.ThresholdsPojo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtils {

	private static final Logger logger = LoggerFactory.getLogger(MapperUtils.class);

	private MapperUtils() {
	}

	public static <T> T convertJsonToPojo(String json, Class<T> clazz) {

		T pojo = null;

		if (!StringUtils.isEmpty(json)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				pojo = mapper.readValue(json, clazz);
			} catch (IOException e) {
				logger.error("Error during converting json in to pojo", e);
			}
		} else {
			logger.warn("Json is Empty");
		}

		return pojo;
	}
	
	public static List<ThresholdsPojo> convertJsonArrayToThresholdsPojo(String json) {

		ObjectMapper mapper = new ObjectMapper();
		List<ThresholdsPojo> list = null;
		
		if (!StringUtils.isEmpty(json)) {
			try {
				list = mapper.readValue(json, new TypeReference<List<ThresholdsPojo>>(){});
			} catch (IOException e) {
				logger.error("Error during converting json in to pojo", e);
			}
		} else {
			logger.warn("Json is Empty");
		}
		
		return list;
	}
}
