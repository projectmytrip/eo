package com.eni.ioc.assetintegrity.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssetIntegrityRequest implements Serializable {

	private static final long serialVersionUID = 7343463830530192243L;

	private String id;
	private String richiedente;
	private String richiedenteUid;
	private List<Segnale> signals;
	private String emergenza;
	private String motivation;
	private String omdSap;
	private Date fromDate;
	private Date t;
	private String duration;
	private String tipoRichiesta;
	private String emailType;
	private String notificationSourceWfId;

	public AssetIntegrityRequest() {
	
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRichiedente() {
		return richiedente;
	}
	
	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}
	
	public String getRichiedenteUid() {
		return richiedenteUid;
	}
	
	public void setRichiedenteUid(String richiedenteUid) {
		this.richiedenteUid = richiedenteUid;
	}
	
	public List<Segnale> getSignals() {
		return signals;
	}
	
	public void setSignals(List<Segnale> signals) {
		this.signals = signals;
	}

	public void addSignal(Segnale signal) {
		if ( signals == null) {
			signals = new ArrayList<>();
		}

		signals.add(signal);
	}

	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public Date getT() {
		return t;
	}
	
	public void setT(Date t) {
		this.t = t;
	}
	
	public String getEmergenza() {
		return emergenza;
	}
	
	public void setEmergenza(String emergenza) {
		this.emergenza = emergenza;
	}
	
	public String getMotivation() {
		return motivation;
	}
	
	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}
	
	public String getOmdSap() {
		return omdSap;
	}
	
	public void setOmdSap(String omdSap) {
		this.omdSap = omdSap;
	}
	
	public String getTipoRichiesta() {
		return tipoRichiesta;
	}
	
	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public String getNotificationSourceWfId() {
		return notificationSourceWfId;
	}

	public void setNotificationSourceWfId(String notificationSourceWfId) {
		this.notificationSourceWfId = notificationSourceWfId;
	}
	
}
