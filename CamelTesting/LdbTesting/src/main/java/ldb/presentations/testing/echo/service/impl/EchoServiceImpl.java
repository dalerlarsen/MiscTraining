package ldb.presentations.testing.echo.service.impl;

import javax.annotation.Resource;

import ldb.presentations.testing.echo.service.EchoService;
import ldb.presentations.testing.echo.service.TimestampService;

public class EchoServiceImpl implements EchoService
{
	@Resource
	private TimestampService timestampService;
	
	@Override
	public String echo(String message) throws IllegalArgumentException
	{
		if (message == null)
			throw new IllegalArgumentException("message must not be null");
		
		if (message.trim().length() < 1)
			throw new IllegalArgumentException("trimmed message must not be empty");
		
		if (message.replaceAll("[a-zA-Z]", "").length() > 0)
			throw new IllegalArgumentException("message must contain only letters");
		
		return timestampService.getTimestamp() + ":" + message;
	}
}