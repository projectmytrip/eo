package com.eni.ioc.assetintegrity.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public enum BacklogStatoUtenteOdmEnum {
	ORIG("ORIG"),
	PLAN("PLAN"),
	SUSP("SUSP"),
	READ("READ"),
	ISSU("ISSU");
	
	private String statoUtenteOdm;
	
	private static Map<String, BacklogStatoUtenteOdmEnum> map = new LinkedHashMap<String, BacklogStatoUtenteOdmEnum>();
	static {
		for (BacklogStatoUtenteOdmEnum stato : BacklogStatoUtenteOdmEnum.values()) {
			map.put(stato.getStatoUtenteOdm(), stato);
		}
		map = Collections.unmodifiableMap(map);
	}
	
	private BacklogStatoUtenteOdmEnum(String statoUtenteOdm){
		this.statoUtenteOdm = statoUtenteOdm;
	}
	
	public String getStatoUtenteOdm() {
		return statoUtenteOdm;
	}

	public void setStatoUtenteOdm(String statoUtenteOdm) {
		this.statoUtenteOdm = statoUtenteOdm;
	}

	public static BacklogStatoUtenteOdmEnum getEnum(String stato){
		BacklogStatoUtenteOdmEnum toReturn = null;
		if(map.containsKey(stato)){
			toReturn = map.get(stato);
		}
		return toReturn;
	}
	
}
