package com.eni.ioc.assetintegrity.common;

public class LogTimeTaken {
	private String logId;
	private long startTime;

	public LogTimeTaken() {
		create(null);
	}

	public LogTimeTaken(String logId) {
		create(logId);
	}

	/**
	 *
	 * @return
	 */
	public void create(String logId) {
		this.logId = logId;
		this.startTime = System.currentTimeMillis();
	}

	/**
	 *
	 * @return [long] time-taken
	 */
	public long getTimeTaken() {
		return (System.currentTimeMillis() - startTime);
	}

	@Override
	public String toString() {
		return logId + ", tt=" + getTimeTaken();
	}
}
