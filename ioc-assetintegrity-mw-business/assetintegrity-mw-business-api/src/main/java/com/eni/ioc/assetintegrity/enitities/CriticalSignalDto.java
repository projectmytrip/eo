package com.eni.ioc.assetintegrity.enitities;

import java.util.Date;

// USA PER WF 

public class CriticalSignalDto {

    private String area;
    private String lastStatusChangeDate;
    private String value;
    private String category;
    private String name;
    private String description;
    private String blockInput;
    private String blockInputStatus;
    private String severity;
    private String asset;
    private String wfId;
	private Boolean approved;
	private Boolean chiuso;
	private Date start;
	private Date end;
	private Date attivazione;
	private Date disattivazione;
	private Boolean moc;
	private Date lastUpdateMoc;
	private String uidUpdateMoc;
	private String numeroMoc;
	private Date chiusuraMoc;
	
	public Date getLastUpdateMoc() {
		return lastUpdateMoc;
	}

	public void setLastUpdateMoc(Date lastUpdateMoc) {
		this.lastUpdateMoc = lastUpdateMoc;
	}

	public String getUidUpdateMoc() {
		return uidUpdateMoc;
	}

	public void setUidUpdateMoc(String uidUpdateMoc) {
		this.uidUpdateMoc = uidUpdateMoc;
	}

	public Date getAttivazione() {
		return attivazione;
	}

	public void setAttivazione(Date attivazione) {
		this.attivazione = attivazione;
	}

	public Date getDisattivazione() {
		return disattivazione;
	}

	public void setDisattivazione(Date disattivazione) {
		this.disattivazione = disattivazione;
	}

	public Boolean getMoc() {
		return moc;
	}

	public void setMoc(Boolean moc) {
		this.moc = moc;
	}

	public CriticalSignalDto() {
		// Auto-generated constructor stub
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLastStatusChangeDate() {
		return lastStatusChangeDate;
	}

	public void setLastStatusChangeDate(String lastStatusChangeDate) {
		this.lastStatusChangeDate = lastStatusChangeDate;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBlockInput() {
		return blockInput;
	}

	public void setBlockInput(String blockInput) {
		this.blockInput = blockInput;
	}

	public String getBlockInputStatus() {
		return blockInputStatus;
	}

	public void setBlockInputStatus(String blockInputStatus) {
		this.blockInputStatus = blockInputStatus;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Boolean getChiuso() {
		return chiuso;
	}

	public void setChiuso(Boolean chiuso) {
		this.chiuso = chiuso;
	}
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}

	public String getNumeroMoc() { return numeroMoc; }

	public void setNumeroMoc(String numeroMoc) { this.numeroMoc = numeroMoc; }

	public Date getChiusuraMoc() { return chiusuraMoc; }

	public void setChiusuraMoc(Date chiusuraMoc) { this.chiusuraMoc = chiusuraMoc; }

	@Override
	public String toString() {
		return "CriticalSignalDto [area=" + area + ", lastStatusChangeDate=" + lastStatusChangeDate + ", value=" + value
				+ ", category=" + category + ", name=" + name + ", description=" + description + ", blockInput="
				+ blockInput + ", blockInputStatus=" + blockInputStatus + ", severity=" + severity + ", asset=" + asset
				+ ", wfId=" + wfId + ", approved=" + approved + ", chiuso=" + chiuso + ", start=" + start + ", end=" + end + "]";
	}

}
