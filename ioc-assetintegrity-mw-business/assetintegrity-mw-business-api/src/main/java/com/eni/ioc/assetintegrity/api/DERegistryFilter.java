package com.eni.ioc.assetintegrity.api;

import java.io.Serializable;

public class DERegistryFilter implements Serializable {

    private static final long serialVersionUID = -79550034398776772L;

    private String tipo;
    private String severity;
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
