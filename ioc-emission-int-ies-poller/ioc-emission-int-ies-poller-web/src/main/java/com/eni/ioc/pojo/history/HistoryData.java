package com.eni.ioc.pojo.history;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "CO", "SO2", "NOX", "H2S", "O2", "DIGIT", "HUM", "PRESS", "COT", "POLVERI", "COT_CORR", "CO_CORR",
		"NOX_CORR", "PORTATA_HUM", "PORTATA_HUM_NORM", "PORTATA_SECCA", "PORTATA_SECCA_NORM", "SO2_CORR", "TEMP_FUMI",
		"PORTATA_SECCA_NORM_CORR", "FLUSSO_MASSA_SO2", "FLUSSO_MASSA", "FLUSSO_VOLUME", "FLUSSO_MASSA_CO",
		"FLUSSO_MASSA_NOX", "FLUSSO_MASSA_COT", "POLVERI_CORR", "FLUSSO_MASSA_POLVERI",
		"NO",
		"SO2B",
		"SO2A",
		"VEL_FUMI",
		"VEL_FUMI_NORM",
		"DELTAP",
		"PORTATAMASSICA", "PORTATAVOLUMETRICA"
})
public class HistoryData implements Serializable {

	private static final long serialVersionUID = 1839120865301461744L;

	@JsonProperty("CO")
	private HistoryKpi co;

	@JsonProperty("SO2")
	private HistoryKpi so2;

	@JsonProperty("NOX")
	private HistoryKpi nox;

	@JsonProperty("H2S")
	private HistoryKpi h2s;

	@JsonProperty("O2")
	private HistoryKpi o2;

	@JsonProperty("DIGIT")
	private HistoryKpi digit;

	@JsonProperty("HUM")
	private HistoryKpi hum;

	@JsonProperty("PRESS")
	private HistoryKpi press;

	@JsonProperty("COT")
	private HistoryKpi cot;

	@JsonProperty("POLVERI")
	private HistoryKpi polveri;

	@JsonProperty("COT_CORR")
	private HistoryKpi cotCorr;

	@JsonProperty("CO_CORR")
	private HistoryKpi coCorr;

	@JsonProperty("NOX_CORR")
	private HistoryKpi noxCorr;

	@JsonProperty("PORTATA_HUM")
	private HistoryKpi portataHum;

	@JsonProperty("PORTATA_HUM_NORM")
	private HistoryKpi portataHumNorm;

	@JsonProperty("PORTATA_SECCA")
	private HistoryKpi portataSecca;

	@JsonProperty("PORTATA_SECCA_NORM")
	private HistoryKpi portataSeccaNorm;

	@JsonProperty("SO2_CORR")
	private HistoryKpi so2Corr;

	@JsonProperty("TEMP_FUMI")
	private HistoryKpi tempFumi;

	@JsonProperty("PORTATA_SECCA_NORM_CORR")
	private HistoryKpi portataSeccaNormCorr;

	@JsonProperty("FLUSSO_MASSA_SO2")
	private HistoryKpi flussoMassaSo2;

	@JsonProperty("FLUSSO_MASSA")
	private HistoryKpi flussoMassa;

	@JsonProperty("FLUSSO_VOLUME")
	private HistoryKpi flussoVolume;

	@JsonProperty("FLUSSO_MASSA_CO")
	private HistoryKpi flussoMassaCo;

	@JsonProperty("FLUSSO_MASSA_NOX")
	private HistoryKpi flussoMassaNox;

	@JsonProperty("FLUSSO_MASSA_COT")
	private HistoryKpi flussoMassaCot;

	@JsonProperty("POLVERI_CORR")
	private HistoryKpi polveriCorr;

	@JsonProperty("FLUSSO_MASSA_POLVERI")
	private HistoryKpi flussoMassaPolveri;

	@JsonProperty("NO")
	private HistoryKpi no;

	@JsonProperty("SO2B")
	private HistoryKpi so2b;

	@JsonProperty("SO2A")
	private HistoryKpi so2a;

	@JsonProperty("VEL_FUMI")
	private HistoryKpi velFumi;

	@JsonProperty("VEL_FUMI_NORM")
	private HistoryKpi velFumiNorm;

	@JsonProperty("DELTAP")
	private HistoryKpi deltaP;
	
	@JsonProperty("PORTATAMASSICA")
	private HistoryKpi portataMassica;

	@JsonProperty("PORTATAVOLUMETRICA")
	private HistoryKpi portataVolumetrica;
	
	public HistoryData() {
	}

	public HistoryData(HistoryKpi co, HistoryKpi so2, HistoryKpi nox, HistoryKpi h2s, HistoryKpi o2, HistoryKpi digit, HistoryKpi hum, HistoryKpi press, HistoryKpi cot,
			HistoryKpi polveri, HistoryKpi cotCorr, HistoryKpi coCorr, HistoryKpi noxCorr, HistoryKpi portataHum, HistoryKpi portataHumNorm, HistoryKpi portataSecca,
			HistoryKpi portataSeccaNorm, HistoryKpi so2Corr, HistoryKpi tempFumi, HistoryKpi portataSeccaNormCorr, HistoryKpi flussoMassaSo2,
			HistoryKpi flussoMassa, HistoryKpi flussoVolume, HistoryKpi flussoMassaCo, HistoryKpi flussoMassaNox, HistoryKpi flussoMassaCot,
			HistoryKpi polveriCorr, HistoryKpi flussoMassaPolveri, HistoryKpi no, HistoryKpi so2b, HistoryKpi so2a, HistoryKpi velFumi, HistoryKpi velFumiNorm,
			HistoryKpi deltaP, HistoryKpi portataMassica, HistoryKpi portataVolumetrica) {
		super();
		this.co = co;
		this.so2 = so2;
		this.nox = nox;
		this.h2s = h2s;
		this.o2 = o2;
		this.digit = digit;
		this.hum = hum;
		this.press = press;
		this.cot = cot;
		this.polveri = polveri;
		this.cotCorr = cotCorr;
		this.coCorr = coCorr;
		this.noxCorr = noxCorr;
		this.portataHum = portataHum;
		this.portataHumNorm = portataHumNorm;
		this.portataSecca = portataSecca;
		this.portataSeccaNorm = portataSeccaNorm;
		this.so2Corr = so2Corr;
		this.tempFumi = tempFumi;
		this.portataSeccaNormCorr = portataSeccaNormCorr;
		this.flussoMassaSo2 = flussoMassaSo2;
		this.flussoMassa = flussoMassa;
		this.flussoVolume = flussoVolume;
		this.flussoMassaCo = flussoMassaCo;
		this.flussoMassaNox = flussoMassaNox;
		this.flussoMassaCot = flussoMassaCot;
		this.polveriCorr = polveriCorr;
		this.flussoMassaPolveri = flussoMassaPolveri;
		this.no = no;
		this.so2b = so2b;
		this.so2a = so2a;
		this.velFumi = velFumi;
		this.velFumiNorm = velFumiNorm;
		this.deltaP = deltaP;
		this.portataMassica = portataMassica;
		this.portataVolumetrica = portataVolumetrica;
	}


	@JsonProperty("CO")
	public HistoryKpi getCo() {
		return co;
	}

	@JsonProperty("CO")
	public void setCo(HistoryKpi co) {
		this.co = co;
	}

	@JsonProperty("SO2")
	public HistoryKpi getSo2() {
		return so2;
	}

	@JsonProperty("SO2")
	public void setSo2(HistoryKpi so2) {
		this.so2 = so2;
	}

	@JsonProperty("NOX")
	public HistoryKpi getNox() {
		return nox;
	}

	@JsonProperty("NOX")
	public void setNox(HistoryKpi nox) {
		this.nox = nox;
	}

	@JsonProperty("H2S")
	public HistoryKpi getH2s() {
		return h2s;
	}

	@JsonProperty("H2S")
	public void setH2s(HistoryKpi h2s) {
		this.h2s = h2s;
	}

	@JsonProperty("O2")
	public HistoryKpi getO2() {
		return o2;
	}

	@JsonProperty("O2")
	public void setO2(HistoryKpi o2) {
		this.o2 = o2;
	}

	@JsonProperty("DIGIT")
	public HistoryKpi getDigit() {
		return digit;
	}

	@JsonProperty("DIGIT")
	public void setDigit(HistoryKpi digit) {
		this.digit = digit;
	}

	@JsonProperty("HUM")
	public HistoryKpi getHum() {
		return hum;
	}

	@JsonProperty("HUM")
	public void setHum(HistoryKpi hum) {
		this.hum = hum;
	}

	@JsonProperty("PRESS")
	public HistoryKpi getPress() {
		return press;
	}

	@JsonProperty("PRESS")
	public void setPress(HistoryKpi press) {
		this.press = press;
	}

	@JsonProperty("COT")
	public HistoryKpi getCot() {
		return cot;
	}

	@JsonProperty("COT")
	public void setCot(HistoryKpi cot) {
		this.cot = cot;
	}

	@JsonProperty("POLVERI")
	public HistoryKpi getPolveri() {
		return polveri;
	}

	@JsonProperty("POLVERI")
	public void setPolveri(HistoryKpi polveri) {
		this.polveri = polveri;
	}

	@JsonProperty("COT_CORR")
	public HistoryKpi getCotCorr() {
		return cotCorr;
	}

	@JsonProperty("COT_CORR")
	public void setCotCorr(HistoryKpi cotCorr) {
		this.cotCorr = cotCorr;
	}

	@JsonProperty("CO_CORR")
	public HistoryKpi getCoCorr() {
		return coCorr;
	}

	@JsonProperty("CO_CORR")
	public void setCoCorr(HistoryKpi coCorr) {
		this.coCorr = coCorr;
	}

	@JsonProperty("NOX_CORR")
	public HistoryKpi getNoxCorr() {
		return noxCorr;
	}

	@JsonProperty("NOX_CORR")
	public void setNoxCorr(HistoryKpi noxCorr) {
		this.noxCorr = noxCorr;
	}

	@JsonProperty("PORTATA_HUM")
	public HistoryKpi getPortataHum() {
		return portataHum;
	}

	@JsonProperty("PORTATA_HUM")
	public void setPortataHum(HistoryKpi portataHum) {
		this.portataHum = portataHum;
	}

	@JsonProperty("PORTATA_HUM_NORM")
	public HistoryKpi getPortataHumNorm() {
		return portataHumNorm;
	}

	@JsonProperty("PORTATA_HUM_NORM")
	public void setPortataHumNorm(HistoryKpi portataHumNorm) {
		this.portataHumNorm = portataHumNorm;
	}

	@JsonProperty("PORTATA_SECCA")
	public HistoryKpi getPortataSecca() {
		return portataSecca;
	}

	@JsonProperty("PORTATA_SECCA")
	public void setPortataSecca(HistoryKpi portataSecca) {
		this.portataSecca = portataSecca;
	}

	@JsonProperty("PORTATA_SECCA_NORM")
	public HistoryKpi getPortataSeccaNorm() {
		return portataSeccaNorm;
	}

	@JsonProperty("PORTATA_SECCA_NORM")
	public void setPortataSeccaNorm(HistoryKpi portataSeccaNorm) {
		this.portataSeccaNorm = portataSeccaNorm;
	}

	@JsonProperty("SO2_CORR")
	public HistoryKpi getSo2Corr() {
		return so2Corr;
	}

	@JsonProperty("SO2_CORR")
	public void setSo2Corr(HistoryKpi so2Corr) {
		this.so2Corr = so2Corr;
	}

	@JsonProperty("TEMP_FUMI")
	public HistoryKpi getTempFumi() {
		return tempFumi;
	}

	@JsonProperty("TEMP_FUMI")
	public void setTempFumi(HistoryKpi tempFumi) {
		this.tempFumi = tempFumi;
	}

	@JsonProperty("PORTATA_SECCA_NORM_CORR")
	public HistoryKpi getPortataSeccaNormCorr() {
		return portataSeccaNormCorr;
	}

	@JsonProperty("PORTATA_SECCA_NORM_CORR")
	public void setPortataSeccaNormCorr(HistoryKpi portataSeccaNormCorr) {
		this.portataSeccaNormCorr = portataSeccaNormCorr;
	}

	@JsonProperty("FLUSSO_MASSA_SO2")
	public HistoryKpi getFlussoMassaSo2() {
		return flussoMassaSo2;
	}

	@JsonProperty("FLUSSO_MASSA_SO2")
	public void setFlussoMassaSo2(HistoryKpi flussoMassaSo2) {
		this.flussoMassaSo2 = flussoMassaSo2;
	}

	@JsonProperty("FLUSSO_MASSA")
	public HistoryKpi getFlussoMassa() {
		return flussoMassa;
	}

	@JsonProperty("FLUSSO_MASSA")
	public void setFlussoMassa(HistoryKpi flussoMassa) {
		this.flussoMassa = flussoMassa;
	}

	@JsonProperty("FLUSSO_VOLUME")
	public HistoryKpi getFlussoVolume() {
		return flussoVolume;
	}

	@JsonProperty("FLUSSO_VOLUME")
	public void setFlussoVolume(HistoryKpi flussoVolume) {
		this.flussoVolume = flussoVolume;
	}

	@JsonProperty("FLUSSO_MASSA_CO")
	public HistoryKpi getFlussoMassaCo() {
		return flussoMassaCo;
	}

	@JsonProperty("FLUSSO_MASSA_CO")
	public void setFlussoMassaCo(HistoryKpi flussoMassaCo) {
		this.flussoMassaCo = flussoMassaCo;
	}

	@JsonProperty("FLUSSO_MASSA_NOX")
	public HistoryKpi getFlussoMassaNox() {
		return flussoMassaNox;
	}

	@JsonProperty("FLUSSO_MASSA_NOX")
	public void setFlussoMassaNox(HistoryKpi flussoMassaNox) {
		this.flussoMassaNox = flussoMassaNox;
	}

	@JsonProperty("FLUSSO_MASSA_COT")
	public HistoryKpi getFlussoMassaCot() {
		return flussoMassaCot;
	}

	@JsonProperty("FLUSSO_MASSA_COT")
	public void setFlussoMassaCot(HistoryKpi flussoMassaCot) {
		this.flussoMassaCot = flussoMassaCot;
	}

	@JsonProperty("POLVERI_CORR")
	public HistoryKpi getPolveriCorr() {
		return polveriCorr;
	}

	@JsonProperty("POLVERI_CORR")
	public void setPolveriCorr(HistoryKpi polveriCorr) {
		this.polveriCorr = polveriCorr;
	}

	@JsonProperty("FLUSSO_MASSA_POLVERI")
	public HistoryKpi getFlussoMassaPolveri() {
		return flussoMassaPolveri;
	}

	@JsonProperty("FLUSSO_MASSA_POLVERI")
	public void setFlussoMassaPolveri(HistoryKpi flussoMassaPolveri) {
		this.flussoMassaPolveri = flussoMassaPolveri;
	}

	@JsonProperty("NO")
	public HistoryKpi getNo() {
		return no;
	}

	@JsonProperty("NO")
	public void setNo(HistoryKpi no) {
		this.no = no;
	}

	@JsonProperty("SO2B")
	public HistoryKpi getSo2b() {
		return so2b;
	}

	@JsonProperty("SO2B")
	public void setSo2b(HistoryKpi so2b) {
		this.so2b = so2b;
	}

	@JsonProperty("SO2A")
	public HistoryKpi getSo2a() {
		return so2a;
	}

	@JsonProperty("SO2A")
	public void setSo2a(HistoryKpi so2a) {
		this.so2a = so2a;
	}

	@JsonProperty("VEL_FUMI")
	public HistoryKpi getVelFumi() {
		return velFumi;
	}

	@JsonProperty("VEL_FUMI")
	public void setVelFumi(HistoryKpi velFumi) {
		this.velFumi = velFumi;
	}

	@JsonProperty("VEL_FUMI_NORM")
	public HistoryKpi getVelFumiNorm() {
		return velFumiNorm;
	}

	@JsonProperty("VEL_FUMI_NORM")
	public void setVelFumiNorm(HistoryKpi velFumiNorm) {
		this.velFumiNorm = velFumiNorm;
	}

	@JsonProperty("DELTAP")
	public HistoryKpi getDeltaP() {
		return deltaP;
	}

	@JsonProperty("DELTAP")
	public void setDeltaP(HistoryKpi deltaP) {
		this.deltaP = deltaP;
	}
	
	@JsonProperty("PORTATAMASSICA")
	public HistoryKpi getPortataMassica() {
		return portataMassica;
	}

	@JsonProperty("PORTATAMASSICA")
	public void setPortataMassica(HistoryKpi portataMassica) {
		this.portataMassica = portataMassica;
	}

	@JsonProperty("PORTATAVOLUMETRICA")
	public HistoryKpi getPortataVolumetrica() {
		return portataVolumetrica;
	}

	@JsonProperty("PORTATAVOLUMETRICA")
	public void setPortataVolumetrica(HistoryKpi portataVolumetrica) {
		this.portataVolumetrica = portataVolumetrica;
	}

	@Override
	public String toString() {
		return "HistoryData [co=" + co + ", so2=" + so2 + ", nox=" + nox + ", h2s=" + h2s + ", o2=" + o2 + ", digit="
				+ digit + ", hum=" + hum + ", press=" + press + ", cot=" + cot + ", polveri=" + polveri + ", cotCorr="
				+ cotCorr + ", coCorr=" + coCorr + ", noxCorr=" + noxCorr + ", portataHum=" + portataHum
				+ ", portataHumNorm=" + portataHumNorm + ", portataSecca=" + portataSecca + ", portataSeccaNorm="
				+ portataSeccaNorm + ", so2Corr=" + so2Corr + ", tempFumi=" + tempFumi + ", portataSeccaNormCorr="
				+ portataSeccaNormCorr + ", flussoMassaSo2=" + flussoMassaSo2 + ", flussoMassa=" + flussoMassa
				+ ", flussoVolume=" + flussoVolume + ", flussoMassaCo=" + flussoMassaCo + ", flussoMassaNox="
				+ flussoMassaNox + ", flussoMassaCot=" + flussoMassaCot + ", polveriCorr=" + polveriCorr
				+ ", flussoMassaPolveri=" + flussoMassaPolveri + ", no=" + no + ", so2b=" + so2b + ", so2a=" + so2a
				+ ", velFumi=" + velFumi + ", velFumiNorm=" + velFumiNorm + ", deltaP=" + deltaP + ", portataMassica="
				+ portataMassica + ", portataVolumetrica=" + portataVolumetrica + "]";
	}
}
