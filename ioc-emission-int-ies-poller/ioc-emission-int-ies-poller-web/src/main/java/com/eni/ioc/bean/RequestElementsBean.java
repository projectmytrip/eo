package com.eni.ioc.bean;

import java.util.List;

import com.eni.ioc.pojo.emission.EmissionPojo;
import com.eni.ioc.pojo.flaring.FlaringPojo;
import com.eni.ioc.pojo.history.HistoryPojo;
import com.eni.ioc.pojo.thresholds.ThresholdsPojo;

public class RequestElementsBean {

	private EmissionPojo emission;
	private FlaringPojo flaring;
	private List<ThresholdsPojo> thresholds;
	private List<HistoryPojo> history;

	public RequestElementsBean() {
	}

	public RequestElementsBean(EmissionPojo emission, FlaringPojo flaring, List<ThresholdsPojo> thresholds, List<HistoryPojo> history) {
		super();
		this.emission = emission;
		this.flaring = flaring;
		this.thresholds = thresholds;
		this.history = history;
	}

	public EmissionPojo getEmission() {
		return emission;
	}

	public void setEmission(EmissionPojo emission) {
		this.emission = emission;
	}

	public FlaringPojo getFlaring() {
		return flaring;
	}

	public void setFlaring(FlaringPojo flaring) {
		this.flaring = flaring;
	}

	public List<ThresholdsPojo> getThresholds() {
		return thresholds;
	}

	public void setThresholds(List<ThresholdsPojo> thresholds) {
		this.thresholds = thresholds;
	}

	public List<HistoryPojo> getHistory() {
		return history;
	}

	public void setHistory(List<HistoryPojo> history) {
		this.history = history;
	}

	@Override
	public String toString() {
		return "RequestElementsBean [emission=" + emission + ", flaring=" + flaring + ", thresholds=" + thresholds
				+ ", history=" + history + "]";
	}
}
