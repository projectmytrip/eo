package com.eni.enhop.be.shifthandoverlogbook.exception;

public class GenericException extends Exception
{
	private static final long serialVersionUID = -3180348595429087111L;

	public GenericException(String message)
	{
		super(message);
	}

	public GenericException(Throwable throwable)
	{
		super(throwable);
	}

	public GenericException(String message, Throwable throwable)
	{
		super(message, throwable);
	}

}
