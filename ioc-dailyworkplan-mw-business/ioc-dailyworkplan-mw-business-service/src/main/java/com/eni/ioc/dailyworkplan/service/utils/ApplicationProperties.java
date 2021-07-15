package com.eni.ioc.dailyworkplan.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

	private static String PATH_PROFILE_BY_UIDS;
	private static String profileEndpoint;
	private static String profileServiceUserByUidEndpoint;

	private static ApplicationProperties singletonInstance;

	public static synchronized ApplicationProperties getInstance() {
		if(singletonInstance == null) {
			singletonInstance = new ApplicationProperties();
		}
		return singletonInstance;
	}

	public String getPathProfileByUids() {
		return PATH_PROFILE_BY_UIDS;
	}

	@Value("${profile.service.usersbyuids}")
	public void setPathProfileByUids(String cPATH_PROFILE_BY_UIDS) {
		PATH_PROFILE_BY_UIDS = cPATH_PROFILE_BY_UIDS;
	}

	public String getProfileEndpoint() {
		return profileEndpoint;
	}

	@Value("${profile.service.endpoint}")
	public void setProfileEndpoint(String profileEndpoint) {
		ApplicationProperties.profileEndpoint = profileEndpoint;
	}

	public String getProfileServiceUserByUidEndpoint() {
		return profileServiceUserByUidEndpoint;
	}

	@Value("${profile.service.userbyuid.endpoint}")
	public void setProfileServiceUserByUidEndpoint(String profileServiceUserByUidEndpoint) {
		ApplicationProperties.profileServiceUserByUidEndpoint = profileServiceUserByUidEndpoint;
	}

}