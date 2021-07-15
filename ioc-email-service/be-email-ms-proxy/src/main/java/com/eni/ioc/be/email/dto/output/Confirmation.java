package com.eni.ioc.be.email.dto.output;

import com.eni.ioc.be.email.dto.ConfirmationBean;
import com.eni.ioc.be.email.dto.NotificationServiceConstants.CODES;

public class Confirmation
{
	private CODES	code;
	private String	reason;

	public CODES getCode()
	{
		return code;
	}

	public void setCode(CODES code)
	{
		this.code = code;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * Fills a presentation level Confirmation from a service level ConfirmationBean
	 * 
	 * @param cb to be read
	 */
	public void fromBean(ConfirmationBean cb)
	{
		setCode(cb.getCode());
		setReason(cb.getReason());
	}
}
