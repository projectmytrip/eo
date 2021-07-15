package com.eni.ioc.assetintegrity.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public enum BacklogUnitaEnum {
	G("G."), // giorni
	H("H"), // ore
	MS("MS"), // mesi
	ST("ST"), // settimane
	T("T"), // giorni
	STD("STD"); // ore
	
	private String unita;
	
	private static Map<String, BacklogUnitaEnum> map = new LinkedHashMap<String, BacklogUnitaEnum>();
	static {
		for (BacklogUnitaEnum unita : BacklogUnitaEnum.values()) {
			map.put(unita.getUnita(), unita);
		}
		map = Collections.unmodifiableMap(map);
	}
	
	private BacklogUnitaEnum(String statoUtenteOdm){
		this.unita = statoUtenteOdm;
	}
	
	public String getUnita() {
		return unita;
	}

	public void setUnita(String unita) {
		this.unita = unita;
	}

	public static BacklogUnitaEnum getEnum(String unita){
		BacklogUnitaEnum toReturn = null;
		if(map.containsKey(unita)){
			toReturn = map.get(unita);
		}
		return toReturn;
	}
	
}
