package ldb.presentations.testing.echo.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class TimestampServiceIntTest
{
	@Resource
	private TimestampService timestampService;

	@Test
	public void success() throws Exception
	{
		assertThat("timestamp", timestampService.getTimestamp(), notNullValue());
	}
}