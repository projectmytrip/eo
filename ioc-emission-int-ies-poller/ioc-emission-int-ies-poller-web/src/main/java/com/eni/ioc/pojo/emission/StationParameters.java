package com.eni.ioc.pojo.emission;

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
		"DELTAP"

})
public class StationParameters implements Serializable {

	private static final long serialVersionUID = 4836660254225654697L;

	@JsonProperty("CO")
	private Kpi co;

	@JsonProperty("SO2")
	private Kpi so2;

	@JsonProperty("NOX")
	private Kpi nox;

	@JsonProperty("H2S")
	private Kpi h2s;

	@JsonProperty("O2")
	private Kpi o2;

	@JsonProperty("DIGIT")
	private Kpi digit;

	@JsonProperty("HUM")
	private Kpi hum;

	@JsonProperty("PRESS")
	private Kpi press;

	@JsonProperty("COT")
	private Kpi cot;

	@JsonProperty("POLVERI")
	private Kpi polveri;

	@JsonProperty("COT_CORR")
	private Kpi cotCorr;

	@JsonProperty("CO_CORR")
	private Kpi coCorr;

	@JsonProperty("NOX_CORR")
	private Kpi noxCorr;

	@JsonProperty("PORTATA_HUM")
	private Kpi portataHum;

	@JsonProperty("PORTATA_HUM_NORM")
	private Kpi portataHumNorm;

	@JsonProperty("PORTATA_SECCA")
	private Kpi portataSecca;

	@JsonProperty("PORTATA_SECCA_NORM")
	private Kpi portataSeccaNorm;

	@JsonProperty("SO2_CORR")
	private Kpi so2Corr;

	@JsonProperty("TEMP_FUMI")
	private Kpi tempFumi;

	@JsonProperty("PORTATA_SECCA_NORM_CORR")
	private Kpi portataSeccaNormCorr;

	@JsonProperty("FLUSSO_MASSA_SO2")
	private Kpi flussoMassaSo2;

	@JsonProperty("FLUSSO_MASSA")
	private Kpi flussoMassa;

	@JsonProperty("FLUSSO_VOLUME")
	private Kpi flussoVolume;

	@JsonProperty("FLUSSO_MASSA_CO")
	private Kpi flussoMassaCo;

	@JsonProperty("FLUSSO_MASSA_NOX")
	private Kpi flussoMassaNox;

	@JsonProperty("FLUSSO_MASSA_COT")
	private Kpi flussoMassaCot;

	@JsonProperty("POLVERI_CORR")
	private Kpi polveriCorr;

	@JsonProperty("FLUSSO_MASSA_POLVERI")
	private Kpi flussoMassaPolveri;

	

	@JsonProperty("NO")
	private Kpi no;

	@JsonProperty("SO2B")
	private Kpi so2b;

	@JsonProperty("SO2A")
	private Kpi so2a;

	@JsonProperty("VEL_FUMI")
	private Kpi velFumi;

	@JsonProperty("VEL_FUMI_NORM")
	private Kpi velFumiNorm;

	@JsonProperty("DELTAP")
	private Kpi deltaP;

	
	public StationParameters() {
	}

	public StationParameters(Kpi co, Kpi so2, Kpi nox, Kpi h2s, Kpi o2, Kpi digit, Kpi hum, Kpi press, Kpi cot,
			Kpi polveri, Kpi cotCorr, Kpi coCorr, Kpi noxCorr, Kpi portataHum, Kpi portataHumNorm, Kpi portataSecca,
			Kpi portataSeccaNorm, Kpi so2Corr, Kpi tempFumi, Kpi portataSeccaNormCorr, Kpi flussoMassaSo2,
			Kpi flussoMassa, Kpi flussoVolume, Kpi flussoMassaCo, Kpi flussoMassaNox, Kpi flussoMassaCot,
			Kpi polveriCorr, Kpi flussoMassaPolveri, Kpi no, Kpi so2b, Kpi so2a, Kpi velFumi, Kpi velFumiNorm,
			Kpi deltaP) {
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
	}


	@JsonProperty("CO")
	public Kpi getCo() {
		return co;
	}

	@JsonProperty("CO")
	public void setCo(Kpi co) {
		this.co = co;
	}

	@JsonProperty("SO2")
	public Kpi getSo2() {
		return so2;
	}

	@JsonProperty("SO2")
	public void setSo2(Kpi so2) {
		this.so2 = so2;
	}

	@JsonProperty("NOX")
	public Kpi getNox() {
		return nox;
	}

	@JsonProperty("NOX")
	public void setNox(Kpi nox) {
		this.nox = nox;
	}

	@JsonProperty("H2S")
	public Kpi getH2s() {
		return h2s;
	}

	@JsonProperty("H2S")
	public void setH2s(Kpi h2s) {
		this.h2s = h2s;
	}

	@JsonProperty("O2")
	public Kpi getO2() {
		return o2;
	}

	@JsonProperty("O2")
	public void setO2(Kpi o2) {
		this.o2 = o2;
	}

	@JsonProperty("DIGIT")
	public Kpi getDigit() {
		return digit;
	}

	@JsonProperty("DIGIT")
	public void setDigit(Kpi digit) {
		this.digit = digit;
	}

	@JsonProperty("HUM")
	public Kpi getHum() {
		return hum;
	}

	@JsonProperty("HUM")
	public void setHum(Kpi hum) {
		this.hum = hum;
	}

	@JsonProperty("PRESS")
	public Kpi getPress() {
		return press;
	}

	@JsonProperty("PRESS")
	public void setPress(Kpi press) {
		this.press = press;
	}

	@JsonProperty("COT")
	public Kpi getCot() {
		return cot;
	}

	@JsonProperty("COT")
	public void setCot(Kpi cot) {
		this.cot = cot;
	}

	@JsonProperty("POLVERI")
	public Kpi getPolveri() {
		return polveri;
	}

	@JsonProperty("POLVERI")
	public void setPolveri(Kpi polveri) {
		this.polveri = polveri;
	}

	@JsonProperty("COT_CORR")
	public Kpi getCotCorr() {
		return cotCorr;
	}

	@JsonProperty("COT_CORR")
	public void setCotCorr(Kpi cotCorr) {
		this.cotCorr = cotCorr;
	}

	@JsonProperty("CO_CORR")
	public Kpi getCoCorr() {
		return coCorr;
	}

	@JsonProperty("CO_CORR")
	public void setCoCorr(Kpi coCorr) {
		this.coCorr = coCorr;
	}

	@JsonProperty("NOX_CORR")
	public Kpi getNoxCorr() {
		return noxCorr;
	}

	@JsonProperty("NOX_CORR")
	public void setNoxCorr(Kpi noxCorr) {
		this.noxCorr = noxCorr;
	}

	@JsonProperty("PORTATA_HUM")
	public Kpi getPortataHum() {
		return portataHum;
	}

	@JsonProperty("PORTATA_HUM")
	public void setPortataHum(Kpi portataHum) {
		this.portataHum = portataHum;
	}

	@JsonProperty("PORTATA_HUM_NORM")
	public Kpi getPortataHumNorm() {
		return portataHumNorm;
	}

	@JsonProperty("PORTATA_HUM_NORM")
	public void setPortataHumNorm(Kpi portataHumNorm) {
		this.portataHumNorm = portataHumNorm;
	}

	@JsonProperty("PORTATA_SECCA")
	public Kpi getPortataSecca() {
		return portataSecca;
	}

	@JsonProperty("PORTATA_SECCA")
	public void setPortataSecca(Kpi portataSecca) {
		this.portataSecca = portataSecca;
	}

	@JsonProperty("PORTATA_SECCA_NORM")
	public Kpi getPortataSeccaNorm() {
		return portataSeccaNorm;
	}

	@JsonProperty("PORTATA_SECCA_NORM")
	public void setPortataSeccaNorm(Kpi portataSeccaNorm) {
		this.portataSeccaNorm = portataSeccaNorm;
	}

	@JsonProperty("SO2_CORR")
	public Kpi getSo2Corr() {
		return so2Corr;
	}

	@JsonProperty("SO2_CORR")
	public void setSo2Corr(Kpi so2Corr) {
		this.so2Corr = so2Corr;
	}

	@JsonProperty("TEMP_FUMI")
	public Kpi getTempFumi() {
		return tempFumi;
	}

	@JsonProperty("TEMP_FUMI")
	public void setTempFumi(Kpi tempFumi) {
		this.tempFumi = tempFumi;
	}

	@JsonProperty("PORTATA_SECCA_NORM_CORR")
	public Kpi getPortataSeccaNormCorr() {
		return portataSeccaNormCorr;
	}

	@JsonProperty("PORTATA_SECCA_NORM_CORR")
	public void setPortataSeccaNormCorr(Kpi portataSeccaNormCorr) {
		this.portataSeccaNormCorr = portataSeccaNormCorr;
	}

	@JsonProperty("FLUSSO_MASSA_SO2")
	public Kpi getFlussoMassaSo2() {
		return flussoMassaSo2;
	}

	@JsonProperty("FLUSSO_MASSA_SO2")
	public void setFlussoMassaSo2(Kpi flussoMassaSo2) {
		this.flussoMassaSo2 = flussoMassaSo2;
	}

	@JsonProperty("FLUSSO_MASSA")
	public Kpi getFlussoMassa() {
		return flussoMassa;
	}

	@JsonProperty("FLUSSO_MASSA")
	public void setFlussoMassa(Kpi flussoMassa) {
		this.flussoMassa = flussoMassa;
	}

	@JsonProperty("FLUSSO_VOLUME")
	public Kpi getFlussoVolume() {
		return flussoVolume;
	}

	@JsonProperty("FLUSSO_VOLUME")
	public void setFlussoVolume(Kpi flussoVolume) {
		this.flussoVolume = flussoVolume;
	}

	@JsonProperty("FLUSSO_MASSA_CO")
	public Kpi getFlussoMassaCo() {
		return flussoMassaCo;
	}

	@JsonProperty("FLUSSO_MASSA_CO")
	public void setFlussoMassaCo(Kpi flussoMassaCo) {
		this.flussoMassaCo = flussoMassaCo;
	}

	@JsonProperty("FLUSSO_MASSA_NOX")
	public Kpi getFlussoMassaNox() {
		return flussoMassaNox;
	}

	@JsonProperty("FLUSSO_MASSA_NOX")
	public void setFlussoMassaNox(Kpi flussoMassaNox) {
		this.flussoMassaNox = flussoMassaNox;
	}

	@JsonProperty("FLUSSO_MASSA_COT")
	public Kpi getFlussoMassaCot() {
		return flussoMassaCot;
	}

	@JsonProperty("FLUSSO_MASSA_COT")
	public void setFlussoMassaCot(Kpi flussoMassaCot) {
		this.flussoMassaCot = flussoMassaCot;
	}

	@JsonProperty("POLVERI_CORR")
	public Kpi getPolveriCorr() {
		return polveriCorr;
	}

	@JsonProperty("POLVERI_CORR")
	public void setPolveriCorr(Kpi polveriCorr) {
		this.polveriCorr = polveriCorr;
	}

	@JsonProperty("FLUSSO_MASSA_POLVERI")
	public Kpi getFlussoMassaPolveri() {
		return flussoMassaPolveri;
	}

	@JsonProperty("FLUSSO_MASSA_POLVERI")
	public void setFlussoMassaPolveri(Kpi flussoMassaPolveri) {
		this.flussoMassaPolveri = flussoMassaPolveri;
	}

	@JsonProperty("NO")
	public Kpi getNo() {
		return no;
	}

	@JsonProperty("NO")
	public void setNo(Kpi no) {
		this.no = no;
	}

	@JsonProperty("SO2B")
	public Kpi getSo2b() {
		return so2b;
	}

	@JsonProperty("SO2B")
	public void setSo2b(Kpi so2b) {
		this.so2b = so2b;
	}

	@JsonProperty("SO2A")
	public Kpi getSo2a() {
		return so2a;
	}

	@JsonProperty("SO2A")
	public void setSo2a(Kpi so2a) {
		this.so2a = so2a;
	}

	@JsonProperty("VEL_FUMI")
	public Kpi getVelFumi() {
		return velFumi;
	}

	@JsonProperty("VEL_FUMI")
	public void setVelFumi(Kpi velFumi) {
		this.velFumi = velFumi;
	}

	@JsonProperty("VEL_FUMI_NORM")
	public Kpi getVelFumiNorm() {
		return velFumiNorm;
	}

	@JsonProperty("VEL_FUMI_NORM")
	public void setVelFumiNorm(Kpi velFumiNorm) {
		this.velFumiNorm = velFumiNorm;
	}

	@JsonProperty("DELTAP")
	public Kpi getDeltaP() {
		return deltaP;
	}

	@JsonProperty("DELTAP")
	public void setDeltaP(Kpi deltaP) {
		this.deltaP = deltaP;
	}

	@Override
	public String toString() {
		return "StationParameters [co=" + co + ", so2=" + so2 + ", nox=" + nox + ", h2s=" + h2s + ", o2=" + o2
				+ ", digit=" + digit + ", hum=" + hum + ", press=" + press + ", cot=" + cot + ", polveri=" + polveri
				+ ", cotCorr=" + cotCorr + ", coCorr=" + coCorr + ", noxCorr=" + noxCorr + ", portataHum=" + portataHum
				+ ", portataHumNorm=" + portataHumNorm + ", portataSecca=" + portataSecca + ", portataSeccaNorm="
				+ portataSeccaNorm + ", so2Corr=" + so2Corr + ", tempFumi=" + tempFumi + ", portataSeccaNormCorr="
				+ portataSeccaNormCorr + ", flussoMassaSo2=" + flussoMassaSo2 + ", flussoMassa=" + flussoMassa
				+ ", flussoVolume=" + flussoVolume + ", flussoMassaCo=" + flussoMassaCo + ", flussoMassaNox="
				+ flussoMassaNox + ", flussoMassaCot=" + flussoMassaCot + ", polveriCorr=" + polveriCorr
				+ ", flussoMassaPolveri=" + flussoMassaPolveri + ", no=" + no + ", so2b=" + so2b + ", so2a=" + so2a
				+ ", velFumi=" + velFumi + ", velFumiNorm=" + velFumiNorm + ", deltaP=" + deltaP + "]";
	}

}
