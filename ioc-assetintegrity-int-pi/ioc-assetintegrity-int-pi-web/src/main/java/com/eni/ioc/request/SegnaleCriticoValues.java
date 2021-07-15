package com.eni.ioc.request;

public enum SegnaleCriticoValues {

    AREA("Area"),
    LASTSTATUSCHANGEDATE("LastStatusChange Date"),
    ACTUALBLOCKINPUTDATE("ActualBlockInputDate"),
    VALUE("Value"),
    CATEGORY("Category"),
    NAME("Name"),
    BLOCKINPUT("BlockInput"),
    BLOCKINPUTSTATUS("BlockInput Status"),
    SEVERITY("Severity"),
    DESCRIPTION("Description");
	
	private String field;
	
	private SegnaleCriticoValues(String field) {
		this.field = field;
	}
	
	public String getField() {
		return field;
	}
	
	public static SegnaleCriticoValues getByField(String field){
		for(SegnaleCriticoValues scv: SegnaleCriticoValues.values()){
			if(scv.getField().equalsIgnoreCase(field)){
				return scv;
			}
		}
		return null;
	}
	
}
