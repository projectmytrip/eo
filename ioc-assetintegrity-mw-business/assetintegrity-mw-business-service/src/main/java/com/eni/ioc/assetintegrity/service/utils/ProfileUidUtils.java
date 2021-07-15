package com.eni.ioc.assetintegrity.service.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProfileUidUtils {

	private static final Logger logger = LoggerFactory.getLogger(ProfileUidUtils.class);

	private static final String DEFAULT_DOMAIN = "ASSETINT";

	@Autowired
	ProfileUidUtils() {
	}

	public RolesPojo getUsersByUid(Set<String> uids) throws JsonProcessingException, UnsupportedEncodingException {
		logger.debug("getUsersByUid");
		String profileResponse = HttpClientHelper.callGetUsersByUid(uids);
		logger.info(profileResponse);
		return convertJsonToRolesPojo(profileResponse);
	}

	private RolesPojo convertJsonToRolesPojo(String json) {
		RolesPojo dto = new RolesPojo();

		if(!StringUtils.isEmpty(json)) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				dto = mapper.readValue(json, RolesPojo.class);
			} catch (IOException e) {
				logger.error("Error during converting json in to pojo for json : " + json, e);
			}
		}

		return dto;
	}

}
