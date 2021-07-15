package com.eni.ioc.assetintegrity.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public enum BacklogTipoManutenzioneEnum {
	PREVENTIVA("Preventiva"),
	CORRETTIVA("Correttiva");
	
	private String tipoManutenzione;
	
	private static Map<String, BacklogTipoManutenzioneEnum> map = new LinkedHashMap<String, BacklogTipoManutenzioneEnum>();
	static {
		for (BacklogTipoManutenzioneEnum tipoManutenzione : BacklogTipoManutenzioneEnum.values()) {
			map.put(tipoManutenzione.getTipoManutenzione(), tipoManutenzione);
		}
		map = Collections.unmodifiableMap(map);
	}
	
	private BacklogTipoManutenzioneEnum(String tipoManutenzione){
		this.tipoManutenzione = tipoManutenzione;
	}
	
	public String getTipoManutenzione() {
		return tipoManutenzione;
	}

	public void setUnita(String tipoManutenzione) {
		this.tipoManutenzione = tipoManutenzione;
	}

	public static BacklogTipoManutenzioneEnum getEnum(String tipoManutenzione){
		BacklogTipoManutenzioneEnum toReturn = null;
		if(map.containsKey(tipoManutenzione)){
			toReturn = map.get(tipoManutenzione);
		}
		return toReturn;
	}
	
}
