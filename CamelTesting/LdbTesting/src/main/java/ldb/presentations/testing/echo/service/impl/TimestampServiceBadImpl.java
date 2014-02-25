package ldb.presentations.testing.echo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import ldb.presentations.testing.echo.service.TimestampService;

public class TimestampServiceBadImpl implements TimestampService
{
	@Override
	public String getTimestamp()
	{
		/*
			Problems:
				Can't test different times (to ensure am/pm handled correctly)
				Performance hit with new SimpleDateFormat on every call
		*/
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new Date());
	}
}