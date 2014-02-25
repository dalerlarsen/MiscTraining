package ldb.presentations.testing.time.impl;

import java.util.Date;

import ldb.presentations.testing.time.Clock;

public class ClockImpl implements Clock
{
	@Override
	public Date getDate()
	{
		return new Date();
	}
}