package com.eni.ioc.dailyworkplan.service.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.eni.ioc.dailyworkplan.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProfileUtils {

	private static final Logger logger = LoggerFactory.getLogger(ProfileUtils.class);

	private static final String DEFAULT_DOMAIN = "PSAF";

	@Autowired
	ProfileUtils() {
	}

	public boolean hasWritingPermission(String asset, String domain, String uid, String jwt) {
		if(logger.isDebugEnabled()) {
			logger.debug("hasWritingPermission, uid->" + uid);
		}

		UserPermissionDto upd = hasPermission(asset, domain, uid, jwt);
		return upd != null && upd.isWriting();
	}

	public boolean hasReadingPermission(String asset, String domain, String uid, String jwt) {
		if(logger.isDebugEnabled()) {
			logger.debug("hasReadingPermission, uid->" + uid);
		}

		UserPermissionDto upd = hasPermission(asset, domain, uid, jwt);
		//se esiste ha come minimo diritti di lettura
		return upd != null;
	}

	public UserPermissionDto hasPermission(String asset, String uid, String jwt) {
		return hasPermission(asset, DEFAULT_DOMAIN, uid, jwt);
	}

	//check if user in userMap
	public UserPermissionDto hasPermission(String asset, String domain, String uid, String jwt) {
		if(logger.isDebugEnabled()) {
			logger.debug("hasPermission, uid->" + uid);
		}
		Map<String, UserPermissionDto> userMap = getMap(asset, jwt);

		return userMap.get(uid);
	}

	//actual call to userprofile. Cached
	@Cacheable(value = "PermissionMap", key = "#asset")
	public Map<String, UserPermissionDto> getMap(String asset, String jwt) {
		String profileResponse = HttpClientHelper.callProfileService(jwt, asset, DEFAULT_DOMAIN);
		return convertJsonToDto(profileResponse);
	}

	private Map<String, UserPermissionDto> convertJsonToDto(String json) {
		List<UserPermissionDto> list = convert(json);
		return listToMap(list);
	}

	private Map<String, UserPermissionDto> listToMap(List<UserPermissionDto> list) {
		Map<String, UserPermissionDto> map = new HashMap<>();
		for (UserPermissionDto u : list) {
			map.put(u.getUid(), u);
		}
		return map;
	}

	public RolesPojo getUsersByUid(Set<String> uids) throws JsonProcessingException, UnsupportedEncodingException {
		logger.debug("getUsersByUid");
		String profileResponse = com.eni.ioc.dailyworkplan.service.utils.HttpClientHelper.callGetUsersByUid(uids);
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

	private List<UserPermissionDto> convert(String json) {
		List<UserPermissionDto> res = new ArrayList<>();
		if(!StringUtils.isEmpty(json)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				PermissionResponse response = mapper.readValue(json, PermissionResponse.class);
				res = response.getData();
			} catch (IOException e) {
				logger.error("Error during converting json in to pojo for json : " + json, e);
			}
		}
		return res;
	}

	public UserPojo getUserInfoByUid(String uid) throws JsonProcessingException, UnsupportedEncodingException {
		logger.debug("getUserInfoByUid");
		String response = HttpClientHelper.callGetUserInfoByUid(uid);
		logger.info(response);
		return convertJsonToUserDto(response);
	}

	private UserPojo convertJsonToUserDto(String json) {
		UserPojo dto = new UserPojo();

		if(!StringUtils.isEmpty(json)) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				dto = mapper.readValue(json, UserPojo.class);
			} catch (IOException e) {
				logger.error("Error during converting json in to dto for json : " + json, e);
			}
		}

		return dto;
	}
}
