package com.eni.ioc.dailyworkplan.utils;

import static com.eni.ioc.dailyworkplan.service.utils.ProfileContstants.AUTHORIZATION_TYPE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JWTUtils {

	private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	private static final String JWT_UID_REGEX = "\"sub\":\"(.*?)\"";
	private static final String JWT_NAME_REGEX = "\"given_name\":\"(.*?)\"";
	private static final String JWT_SURNAME_REGEX = "\"family_name\":\"(.*?)\"";

	private static final String AUTHORIZATION_HEADER = "Authorization";

	private JWTUtils() {
	}

	public static String getUid(String jwt) {

		String uid = "";

		String decodedJWT = decodeJWT(jwt);

		Pattern pattern = Pattern.compile(JWT_UID_REGEX);
		Matcher matcher = pattern.matcher(decodedJWT);

		if(matcher.find()) {
			uid = matcher.group(1);
		}

		return uid;
	}

	public static String getUser(String jwt) {

		String name = "";
		String surname = "";

		String decodedJWT = decodeJWT(jwt);

		Pattern patternName = Pattern.compile(JWT_NAME_REGEX);
		Pattern patternSurname = Pattern.compile(JWT_SURNAME_REGEX);

		Matcher matcherName = patternName.matcher(decodedJWT);
		Matcher matcherSurname = patternSurname.matcher(decodedJWT);

		if(matcherName.find()) {
			name = matcherName.group(1);
		}

		if(matcherSurname.find()) {
			surname = matcherSurname.group(1);
		}

		return name + " " + surname;
	}

	public static String retrieveUidFromJWT(String jwt) {
		return JWTUtils.getUid(jwt);
	}

	public static String getJWTfromHeader(HttpServletRequest request) {
		String authorization = request.getHeader(AUTHORIZATION_HEADER);
		logger.info(authorization);
		String jwt = "";
		if(authorization != null) {
			jwt = authorization.substring(AUTHORIZATION_TYPE.length()).trim();
		}
		return jwt;
	}

	private static String decodeJWT(String jwt) {

		byte[] byteArray = Base64.decodeBase64(jwt.getBytes());
		return new String(byteArray);
	}

}
