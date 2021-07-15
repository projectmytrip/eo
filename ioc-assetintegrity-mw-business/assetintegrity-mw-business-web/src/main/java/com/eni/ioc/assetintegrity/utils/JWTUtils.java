package com.eni.ioc.assetintegrity.utils;

import static com.eni.ioc.assetintegrity.service.utils.ProfileContstants.AUTHORIZATION_TYPE;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.eni.ioc.assetintegrity.service.utils.ApplicationProperties;

public class JWTUtils {

	private static final Logger	logger	= LoggerFactory.getLogger(JWTUtils.class);
	
	private static final String JWT_UID_REGEX = "\"sub\":\"(.*?)\"";
	private static final String JWT_NAME_REGEX = "\"given_name\":\"(.*?)\"";
	private static final String JWT_SURNAME_REGEX = "\"family_name\":\"(.*?)\"";
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	
	private static RSAPublicKey publicKey;

	private JWTUtils() {
	}

	public static String getUid(String jwt) {

		String uid = "";

		String decodedJWT = decodeJWT(jwt);

		Pattern pattern = Pattern.compile(JWT_UID_REGEX);
		Matcher matcher = pattern.matcher(decodedJWT);

		if (matcher.find()) {
			uid = matcher.group(1);
		}

		return uid;
	}

	public static String retrieveUidFromJWT(String jwt) {
		return JWTUtils.getUid(jwt);
	}
	
	public static String getUser(String jwt) {

		String name = "";
		String surname = "";

		String decodedJWT = decodeJWT(jwt);

		Pattern patternName = Pattern.compile(JWT_NAME_REGEX);
		Pattern patternSurname = Pattern.compile(JWT_SURNAME_REGEX);

		Matcher matcherName = patternName.matcher(decodedJWT);
		Matcher matcherSurname = patternSurname.matcher(decodedJWT);
		
		if (matcherName.find()) {
			name = matcherName.group(1);
		}
		
		if (matcherSurname.find()) {
			surname = matcherSurname.group(1);
		}

		return name + " " + surname;
	}
	
	public static boolean checkJWT(String jwt) {
                if (jwt == null || jwt.isEmpty()) {
                    logger.info("jwt nullo");
                    return false;
                }
		try {
			
			if(publicKey == null)
				loadPublicKey();
			
			Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwt);
			logger.info("jwt valido");
			return true;
		} catch (ExpiredJwtException ex) {
			logger.info(jwt);
			logger.info("jwt valido ma scaduto");
			//logger.warn("ExpiredJwtException", ex);
			return true;
		} catch (InvalidClaimException ex) {
			logger.info(jwt);
			logger.info("jwt non valido");
			logger.error("InvalidClaimException", ex);
			return false;
		} catch (JwtException ex) {
			logger.info(jwt);
			logger.info("jwt non valido - generic");
			logger.error("JwtException", ex);
			return false;
		}
	}
	
	public static String getJWTfromHeader(HttpServletRequest request) {
		String authorization = request.getHeader(AUTHORIZATION_HEADER);
		String jwt = "";
		if (authorization != null) {
			jwt = authorization.substring(AUTHORIZATION_TYPE.length()).trim();
		}
		return jwt;
	}
	
	/*public static boolean hasJWTToken(HttpServletRequest request) {
		String authorization = request.getHeader(AUTHORIZATION_HEADER);
		return authorization != null && authorization.substring(AUTHORIZATION_TYPE.length()).trim() != "";
	}*/
	
	private static String decodeJWT(String jwt) {

		byte[] byteArray = Base64.decodeBase64(jwt.getBytes());
		return new String(byteArray);
	}
	
	private static void loadPublicKey() {
		String publicKeyContent = null;
		try {
			
			String fileName = ApplicationProperties.getInstance().getPublicKeyJwt();
			publicKeyContent =  StreamUtils.copyToString(new ClassPathResource(fileName).getInputStream(), Charset.defaultCharset());

		} catch (IOException e) {
			logger.error("Errore nella lettura del file .pem con chiave pubblica");
		}
		
		if (null != publicKeyContent) {
			publicKeyContent = publicKeyContent.replaceAll("\\n", "").replaceAll("\\r", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace(" ", "+");
		}
		
        KeyFactory kf = null;
        
		try {
			kf = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException", e);
		}
		logger.info(publicKeyContent);
		
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = null;
        
        try {
        	pubKey = null != kf ? (RSAPublicKey) kf.generatePublic(keySpecX509) : null;
		} catch (InvalidKeySpecException e) {
			logger.error("InvalidKeySpecException", e);
		}
        
        publicKey = pubKey;
	}
}
