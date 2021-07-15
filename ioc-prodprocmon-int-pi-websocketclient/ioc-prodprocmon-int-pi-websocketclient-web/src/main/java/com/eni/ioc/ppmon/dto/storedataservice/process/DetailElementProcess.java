package com.eni.ioc.ppmon.dto.storedataservice.process;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DetailElementProcess implements Serializable {

	private static final long serialVersionUID = 957662061925705037L;
	
    private String value;
    private String url;
    private LocalDateTime datetime;

    public DetailElementProcess() {
    }
    
    public DetailElementProcess(String value, String url, LocalDateTime datetime) {
        super();
        this.value = value;
        this.url = url;
        this.datetime = datetime;
    }
    
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

	@Override
	public String toString() {
		return "DetailElementProcess [value=" + value + ", url=" + url + ", datetime=" + datetime + "]";
	}
}
