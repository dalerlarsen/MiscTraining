package ldb.presentations.testing.echo.service.impl;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import ldb.presentations.testing.echo.service.TimestampService;
import ldb.presentations.testing.time.Clock;

public class TimestampServiceImpl implements TimestampService
{
	@Resource
	private Clock clock;
	
	@Override
	public String getTimestamp()
	{
		//TODO performance hit because of new SimpleDateFormat. When fix, make sure thread safe!
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(clock.getDate());
	}
}