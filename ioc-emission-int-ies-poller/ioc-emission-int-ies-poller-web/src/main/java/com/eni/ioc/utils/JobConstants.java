package com.eni.ioc.utils;

public abstract class JobConstants {

	private JobConstants() {
	}

	public static final String JOB_EMISSION_IDENTITY = "crontabPersistJobEmissions";
	public static final String JOB_FLARING_IDENTITY = "crontabPersistJobFlaring";
	public static final String JOB_SERVER_MINUTE_IDENTITY = "crontabPersistJobServerMinute";
	public static final String JOB_SERVER_HOUR_IDENTITY = "crontabPersistJobServerHour";
	public static final String JOB_SERVER_DAY_IDENTITY = "crontabPersistJobServerDay";
}
