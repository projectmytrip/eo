package com.eni.ioc.client.response;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

public class ProductDto implements Serializable {

	
	public static final String DWP_HC_KEYNAME = "DWP_HC";
	public static final String DWP_H2O_KEYNAME = "DWP_H2O";
	public static final String CO2_KEYNAME = "CO2_ActualValue";
	public static final String H2S_KEYNAME = "H2S_Concentration";
	public static final String WOBBE_KEYNAME = "WOBBE_ActualValue";
	public static final String SULFUR_HRC_KEYNAME = "Sulfur_HRC";
	public static final String SULFUR_TOTAL_KEYNAME = "Sulfur_Total";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyname;
	private String contentValue;
	private String unitsAbbreviation;
	private HashMap<String, String> hmap = new HashMap<>();
	private Map<String, Integer> orderList = new HashMap<>();

	public ProductDto() {
		hmap.put(CO2_KEYNAME, "CO2");
		hmap.put(DWP_HC_KEYNAME, "HC dew point");
		hmap.put(DWP_H2O_KEYNAME, "H2O dew point");
		hmap.put(H2S_KEYNAME, "H2S");
		hmap.put(WOBBE_KEYNAME, "WOBBE Index");
		hmap.put(SULFUR_HRC_KEYNAME, "ZOLFO HRC");
		hmap.put(SULFUR_TOTAL_KEYNAME, "ZOLFO totale");
		initOrderList();
	}

	public ProductDto(String keyName, String contentValue) {
		this.setKeyname(keyName);
		this.setContentValue(contentValue);
		this.setUnitsAbbreviation("");
		initOrderList();
	}
	
	public int elaborateSort() {
		return this.orderList.get(keyname);
	}

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	public String getContentValue() {
		return contentValue;
	}

	public void setContentValue(String contentValue) {
		this.contentValue = contentValue;
	}

	public String getUnitsAbbreviation() {
		return unitsAbbreviation;
	}

	public void setUnitsAbbreviation(String unitsAbbreviation) {
		this.unitsAbbreviation = unitsAbbreviation;
	}

	private void initOrderList() {
		orderList.put(DWP_HC_KEYNAME, 1);
		orderList.put(DWP_H2O_KEYNAME, 2);
		orderList.put(CO2_KEYNAME, 3);
		orderList.put(H2S_KEYNAME, 4);
		orderList.put(WOBBE_KEYNAME, 5);
		orderList.put(SULFUR_HRC_KEYNAME, 6);
		orderList.put(SULFUR_TOTAL_KEYNAME, 7);
	}
}
