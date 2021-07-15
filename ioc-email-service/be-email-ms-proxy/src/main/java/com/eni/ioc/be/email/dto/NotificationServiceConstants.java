package com.eni.ioc.be.email.dto;

public class NotificationServiceConstants {

	private static final long serialVersionUID = 1L;

	public static enum CODES {
		DELIVERED, ERROR;

		@Override
		public String toString() {
			return this.name();
		}
	};

}
