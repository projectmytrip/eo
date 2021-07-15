package com.eni.ioc.be.email.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProfileUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileUtils.class);

	@Cacheable(value = "MailinglistEmail", key = "#asset.concat(#mailinglistName)")
	public RolesPojo getMailiglistByEmail(String asset, String mailinglistName) throws JsonProcessingException, UnsupportedEncodingException {
		logger.debug("getMailiglistByEmail");
		String profileResponse = HttpClientHelper.callGetMailiglistByEmail(asset, mailinglistName);
		logger.info(profileResponse);
		return convertJsonToDto(profileResponse);
	}
	
	private RolesPojo convertJsonToDto(String json) {
		RolesPojo dto = new RolesPojo();

		if (!StringUtils.isEmpty(json)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				dto = mapper.readValue(json, RolesPojo.class);
			} catch (IOException e) {
				logger.error("Error during converting json in to pojo for json : " + json, e);
			}
		}

		return dto;
	}
}
