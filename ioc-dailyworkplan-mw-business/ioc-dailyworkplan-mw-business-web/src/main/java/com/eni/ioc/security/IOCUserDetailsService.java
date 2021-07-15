package com.eni.ioc.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.eni.ioc.dailyworkplan.service.utils.ProfileUtils;
import com.eni.ioc.dailyworkplan.service.utils.UserPermissionDto;

@Service
public class IOCUserDetailsService implements UserDetailsService {
	
	@Autowired
	ProfileUtils profileUtils;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		//retrieve userdata from ProfileUtils
		String[] tokens = username.split("\\|");
		Map<String, UserPermissionDto> usersMap = profileUtils.getMap(tokens[0], tokens[2]);
		return usersMap.get(tokens[1]);
	}

}
