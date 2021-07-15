package com.eni.ioc.ppmon.dto.storedataservice;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DetailElement implements Serializable {

	private static final long serialVersionUID = 957662061925705037L;
	
    private String value;
    private LocalDateTime datetime;

    public DetailElement() {
    }
    
    public DetailElement(String value, LocalDateTime datetime) {
        super();
        this.value = value;
        this.datetime = datetime;
    }
    
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

	@Override
	public String toString() {
		return "DetailElement [value=" + value + ", datetime=" + datetime + "]";
	}
}
