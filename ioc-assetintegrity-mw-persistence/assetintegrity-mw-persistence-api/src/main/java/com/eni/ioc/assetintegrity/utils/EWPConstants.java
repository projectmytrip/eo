package com.eni.ioc.assetintegrity.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EWPConstants {

	public static final String APERTURAEWP = "APERTURA";
	public static final String SCADUTOEWP = "SCADENZA";
	public static final String RINNOVOEWP = "RINNOVO";
	public static final String SOSPENSIONEEWP = "SOSPENSIONE";
	public static final String CHIUSURAEWP = "CHIUSURA";
	
	private static Map<String, List<String>> ewpStateMap = new HashMap<>();
	
	static {
		//APERTURA e RINNOVO
		List<String> apertura = new ArrayList<>();
		apertura.add(SOSPENSIONEEWP);
		apertura.add(SCADUTOEWP);
		ewpStateMap.put(APERTURAEWP, apertura);
		ewpStateMap.put(RINNOVOEWP, apertura);
		
		//SCADENZA e SOSPENSIONE
		List<String> sospScad = new ArrayList<>();
		sospScad.add(RINNOVOEWP);
		sospScad.add(CHIUSURAEWP);
		ewpStateMap.put(SCADUTOEWP, sospScad);
		ewpStateMap.put(SOSPENSIONEEWP, sospScad);
		
		//CHIUSURA -> non può cambiare stato
	}
	
	public static Map<String, List<String>> getEwpStateMap() {
		return ewpStateMap;
	}
	
	private EWPConstants() {}
}
