package com.eni.ioc.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SegnaleCritico implements Serializable{
    
    	private static final long serialVersionUID = 4620379510248555928L;
	
	    @JsonProperty("Area")
	    private String area;
	    
	    @JsonProperty("LastStatusChangeDate")
	    private String lastStatusChangeDate;
	    
	    @JsonProperty("Value")
	    private String value;
	    
	    @JsonProperty("Category")
	    private String category;
	    
	    @JsonProperty("Name")
	    private String name;
	    
	    @JsonProperty("BlockInput")
	    private String blockInput;
	    
	    @JsonProperty("BlockInput Status")
	    private String blockInputStatus;
	    
	    @JsonProperty("Severity")
	    private String severity;
	    
	    @JsonProperty("Description")
	    private String description;
            	    
	    public SegnaleCritico() {}

		public SegnaleCritico(String area, String lastStatusChangeDate, String value, String category, String name,
				String blockInput, String blockInputStatus, String severity, String description) {
			super();
			this.area = area;
			this.lastStatusChangeDate = lastStatusChangeDate;
			this.value = value;
			this.category = category;
			this.name = name;
			this.blockInput = blockInput;
			this.blockInputStatus = blockInputStatus;
			this.severity = severity;
			this.description = description;
		}

		@JsonProperty("Area")
		public String getArea() {
			return area;
		}

		@JsonProperty("Area")
		public void setArea(String area) {
			this.area = area;
		}

		@JsonProperty("LastStatusChangeDate")
		public String getLastStatusChangeDate() {
			return lastStatusChangeDate;
		}

		@JsonProperty("LastStatusChangeDate")
		public void setLastStatusChangeDate(String lastStatusChangeDate) {
			this.lastStatusChangeDate = lastStatusChangeDate;
		}
                
		@JsonProperty("Value")
		public String getValue() {
			return value;
		}

		@JsonProperty("Value")
		public void setValue(String value) {
			this.value = value;
		}

		@JsonProperty("Category")
		public String getCategory() {
			return category;
		}

		@JsonProperty("Category")
		public void setCategory(String category) {
			this.category = category;
		}

		@JsonProperty("Name")
		public String getName() {
			return name;
		}

		@JsonProperty("Name")
		public void setName(String name) {
			this.name = name;
		}

		@JsonProperty("BlockInput")
		public String getBlockInput() {
			return blockInput;
		}

		@JsonProperty("BlockInput")
		public void setBlockInput(String blockInput) {
			this.blockInput = blockInput;
		}

		@JsonProperty("BlockInput Status")
		public String getBlockInputStatus() {
			return blockInputStatus;
		}

		@JsonProperty("BlockInput Status")
		public void setBlockInputStatus(String blockInputStatus) {
			this.blockInputStatus = blockInputStatus;
		}

		@JsonProperty("Severity")
		public String getSeverity() {
			return severity;
		}

		@JsonProperty("Severity")
		public void setSeverity(String severity) {
			this.severity = severity;
		}

		@JsonProperty("Description")
		public String getDescription() {
			return description;
		}

		@JsonProperty("Description")
		public void setDescription(String description) {
			this.description = description;
		}

}
