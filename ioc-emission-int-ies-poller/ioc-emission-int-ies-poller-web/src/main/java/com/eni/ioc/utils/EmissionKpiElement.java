package com.eni.ioc.utils;

public enum EmissionKpiElement {

	CO(1),
	SO2(1),
	NOX(1),
	H2S(1),
	O2(1),
	DIGIT(1),
	HUM(1),
	PRESS(1),
	COT(1),
	POLVERI(1),
	COT_CORR(2),
	CO_CORR(2),
	NOX_CORR(2),
	PORTATA_HUM(2),
	PORTATA_HUM_NORM(2),
	PORTATA_SECCA(2),
	PORTATA_SECCA_NORM(2),
	SO2_CORR(2),
	TEMP_FUMI(2),
	PORTATA_SECCA_NORM_CORR(2),
	FLUSSO_MASSA_SO2(3),
	FLUSSO_MASSA(3),
	FLUSSO_VOLUME(3),
	FLUSSO_MASSA_CO(3),
	FLUSSO_MASSA_NOX(3),
	FLUSSO_MASSA_COT(3),
	POLVERI_CORR(3),
	FLUSSO_MASSA_POLVERI(3);
	
	private int index;
	
	public int getIndex() {
		return this.index;
	}
	
	private EmissionKpiElement(int index) {
		this.index = index;
	}
}
