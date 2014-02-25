package ldb.presentations.testing.echo.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import ldb.presentations.testing.time.Clock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class TimestampServiceUnitTest
{
	@Resource
	private TimestampService timestampService;
	
	@Resource
	private Clock clockMock;

	private void invokeGetTimestamp(String date) throws ParseException
	{
		when(clockMock.getDate()).thenReturn(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse(date));
		String timestamp = timestampService.getTimestamp();
		assertThat("timestamp", timestamp, notNullValue());
		assertThat("timestamp format", timestamp, equalTo(date));
	}
	
	@Test
	public void timeIsAm() throws Exception
	{
		invokeGetTimestamp("2001/01/31 01:59:59.100");
	}
	
	@Test
	public void timeIsPm() throws Exception
	{
		invokeGetTimestamp("2001/01/31 23:59:59.100");
	}
}