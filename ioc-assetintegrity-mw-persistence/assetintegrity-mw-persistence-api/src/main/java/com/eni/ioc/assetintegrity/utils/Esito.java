package com.eni.ioc.assetintegrity.utils;

import java.util.ArrayList;

public class Esito<T> {

	private EsitoEnum statoEsecuzione;
	private ArrayList<String> messageCollector;
	private T data;

	public Esito(EsitoEnum statoEsecuzione, ArrayList<String> messageCollector) {

		this.statoEsecuzione = statoEsecuzione;
		this.messageCollector = messageCollector;
	}

	public Esito(EsitoEnum statoEsecuzione) {

		this.messageCollector = this.getMessageCollector();
	}

	public Esito() {
		this.statoEsecuzione = EsitoEnum.STARTING;
	}

	public EsitoEnum getStatoEsecuzione() {
		return statoEsecuzione;
	}

	public void setStatoEsecuzione(EsitoEnum statoEsecuzione) {
		this.statoEsecuzione = statoEsecuzione;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ArrayList<String> getMessageCollector() {
		if (messageCollector == null) {
			setMessageCollector(new ArrayList<String>());
			return messageCollector;
		}
		return messageCollector;
	}

	public void setMessageCollector(ArrayList<String> messageCollector) {
		this.messageCollector = messageCollector;
	}

	public enum EsitoEnum {
		// @formatter:off
		OK, KO, INDEFINITO, STARTING;
	}
}