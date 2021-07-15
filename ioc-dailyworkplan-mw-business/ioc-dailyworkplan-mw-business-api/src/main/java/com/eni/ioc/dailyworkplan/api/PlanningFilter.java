package com.eni.ioc.dailyworkplan.api;

import java.io.Serializable;

public class PlanningFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324359854322778126L;
	private String fromDate;
	private String toDate;
	private Long planningId;
	private String confirmationBy;
	private String tipoConferma;
	private String note;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Long getPlanningId() {
		return planningId;
	}

	public void setPlanningId(Long planningId) {
		this.planningId = planningId;
	}

	public String getConfirmationBy() {
		return confirmationBy;
	}

	public void setConfirmationBy(String confirmationBy) {
		this.confirmationBy = confirmationBy;
	}

	public String getTipoConferma() {
		return tipoConferma;
	}

	public void setTipoConferma(String tipoConferma) {
		this.tipoConferma = tipoConferma;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
