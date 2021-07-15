package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.util.Date;

public class NotificationDto implements Serializable {

	private static final long serialVersionUID = 1252352L;

	public static final int LEVEL_ALERT = 0;
	public static final int LEVEL_WARNING = 1;
	public static final int LEVEL_INFO = 2;

	public static final int WF_STATUS_APERTO = 0;
	public static final int WF_STATUS_CHIUSO = 1;
	public static final int WF_STATUS_CHIUSOFORZATAMENTE = 2;
	public static final int WF_STATUS_NA = 3;

	private Long id;
	private String channel;
	private String text;
	private Date notifyDate;
	private String workflow;
	private int status;
	private String vista;
	private String step;
	private String asset;
	private int level;
	private String type;
	private String severity;
	private String state;
	private boolean isCalendar;

	public NotificationDto() {

	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(Date notifyDate) {
		this.notifyDate = notifyDate;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVista() {
		return vista;
	}

	public void setVista(String vista) {
		this.vista = vista;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCalendar(boolean isCalendar) {
		this.isCalendar = isCalendar;
	}

	public boolean isCalendar() {
		return isCalendar;
	}

	@Override
	public String toString() {
		return "NotificationDto={id=" + this.id + ",channel=" + this.channel + ",text=" + this.text +   ",notifyDate=" + this.notifyDate + ",workflow=" + this.workflow + ",status=" + this.status + ",vista=" + this.vista + ",step=" + this.step + ",asset=" + this.asset + ",level=" + this.level + ",type=" + this.type + ",severity=" + this.severity + ",state=" + this.state + "}";
	}

}
