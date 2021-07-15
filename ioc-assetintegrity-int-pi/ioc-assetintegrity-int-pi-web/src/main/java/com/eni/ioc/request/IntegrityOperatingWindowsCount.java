package com.eni.ioc.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntegrityOperatingWindowsCount {

    @JsonProperty("IOW")
    private Float IOW;

    @JsonProperty("IOW_OK")
    private Float IOW_OK;

    @JsonProperty("IOW_OUT")
    private Float IOW_OUT;

	@JsonProperty("IOW_WARNING")
	private Float IOW_WARNING;

    public Float getIOW() {
        return IOW;
    }

    public void setIOW(Float IOW) {
        this.IOW = IOW;
    }

    public Float getIOW_OK() {
        return IOW_OK;
    }

    public void setIOW_OK(Float IOW_OK) {
        this.IOW_OK = IOW_OK;
    }

    public Float getIOW_OUT() {
        return IOW_OUT;
    }

    public void setIOW_OUT(Float IOW_OUT) {
        this.IOW_OUT = IOW_OUT;
    }

	public Float getIOW_WARNING() {
		return IOW_WARNING;
	}

	public void setIOW_WARNING(Float IOW_WARNING) {
		this.IOW_WARNING = IOW_WARNING;
	}
}

