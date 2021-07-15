package com.eni.ioc.pojo.flaring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "PORTATAMASSICA", "PORTATAVOLUMETRICA", "FLUSSO_VOLUME", "FLUSSO_MASSA" })
public class StationParameters implements Serializable {

	private static final long serialVersionUID = 4836660254225654697L;

	@JsonProperty("PORTATAMASSICA")
	private Kpi portataMassica;

	@JsonProperty("PORTATAVOLUMETRICA")
	private Kpi portataVolumetrica;

	@JsonProperty("FLUSSO_VOLUME")
	private Kpi flussoVolume;

	@JsonProperty("FLUSSO_MASSA")
	private Kpi flussoMassa;
	
	

	public StationParameters() {
	}



	public StationParameters(Kpi portataMassica, Kpi portataVolumetrica, Kpi flussoVolume, Kpi flussoMassa) {
		super();
		this.portataMassica = portataMassica;
		this.portataVolumetrica = portataVolumetrica;
		this.flussoVolume = flussoVolume;
		this.flussoMassa = flussoMassa;
	}


	@JsonProperty("PORTATAMASSICA")
	public Kpi getPortataMassica() {
		return portataMassica;
	}

	@JsonProperty("PORTATAMASSICA")
	public void setPortataMassica(Kpi portataMassica) {
		this.portataMassica = portataMassica;
	}

	@JsonProperty("PORTATAVOLUMETRICA")
	public Kpi getPortataVolumetrica() {
		return portataVolumetrica;
	}

	@JsonProperty("PORTATAVOLUMETRICA")
	public void setPortataVolumetrica(Kpi portataVolumetrica) {
		this.portataVolumetrica = portataVolumetrica;
	}

	@JsonProperty("FLUSSO_VOLUME")
	public Kpi getFlussoVolume() {
		return flussoVolume;
	}

	@JsonProperty("FLUSSO_VOLUME")
	public void setFlussoVolume(Kpi flussoVolume) {
		this.flussoVolume = flussoVolume;
	}

	@JsonProperty("FLUSSO_MASSA")
	public Kpi getFlussoMassa() {
		return flussoMassa;
	}

	@JsonProperty("FLUSSO_MASSA")
	public void setFlussoMassa(Kpi flussoMassa) {
		this.flussoMassa = flussoMassa;
	}



	@Override
	public String toString() {
		return "StationParameters [portataMassica=" + portataMassica + ", portataVolumetrica=" + portataVolumetrica
				+ ", flussoVolume=" + flussoVolume + ", flussoMassa=" + flussoMassa + "]";
	}


}
