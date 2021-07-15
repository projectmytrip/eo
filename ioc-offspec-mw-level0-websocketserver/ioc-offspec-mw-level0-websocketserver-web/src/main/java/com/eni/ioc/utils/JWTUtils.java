package com.eni.ioc.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


public class JWTUtils {

	private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	private static RSAPublicKey publicKey;
	
	public JWTUtils(){
		if(publicKey == null)
			loadPublicKey();	
	}



	public static void loadPublicKey() {
		String publicKeyContent = null;
		try {
			
			String fileName = CustomConfigurations.getProperty("publicKey.jwt");		
			publicKeyContent =  StreamUtils.copyToString(new ClassPathResource(fileName).getInputStream(), Charset.defaultCharset());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Errore nella lettura del file .pem con chiave pubblica");
		}
		
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replaceAll("\\r", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace(" ", "+");
        KeyFactory kf = null;
        
		try {
			kf = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(publicKeyContent);
		
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = null;
        
        try {
        	pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        publicKey = pubKey;
        
	}


	public static RSAPublicKey getPublicKey() {
		return publicKey;
	}
	
	
	public static Boolean checkJWT(String jwt) {
		try {
			Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwt);
			logger.info("jwt valido");
			return true;
		} catch (ExpiredJwtException ex) {
			logger.info("jwt valido ma scaduto");
			return true;
		} catch (InvalidClaimException ex) {
			logger.info("jwt non valido");
			return false;
		} catch (JwtException ex) {
			logger.info("jwt non valido - generic");
			return false;
		}
	}



}

