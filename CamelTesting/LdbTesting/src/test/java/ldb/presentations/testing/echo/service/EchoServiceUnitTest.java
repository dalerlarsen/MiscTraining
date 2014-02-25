package ldb.presentations.testing.echo.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import javax.annotation.Resource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
	(
		locations =
			{
				"classpath:META-INF/context/echo-context.xml",
				"EchoServiceUnitTest-context.xml"
			}
	)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class EchoServiceUnitTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Resource
	private EchoService echoService;
	
	@Resource TimestampService timestampServiceMock;
	
	@Test
	public void messageIsNull()
	{
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(equalTo("message must not be null"));
		echoService.echo(null);
	}
	
	@Test
	public void messageIsEmpty()
	{
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(equalTo("trimmed message must not be empty"));
		echoService.echo("");
	}
	
	@Test
	public void messageIsBlank()
	{
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(equalTo("trimmed message must not be empty"));
		echoService.echo(" ");
	}
	
	@Test
	public void messageHasNonLetters()
	{
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(equalTo("message must contain only letters"));
		echoService.echo("a1B");
	}
	
	@Test
	public void timestampServiceError()
	{
		RuntimeException toThrow = new RuntimeException("error from mock");
		Mockito.when(timestampServiceMock.getTimestamp()).thenThrow(toThrow);
		expectedException.expect(sameInstance(toThrow));
		echoService.echo("aB");
	}
	
	@Test
	public void success()
	{
		Mockito.when(timestampServiceMock.getTimestamp()).thenReturn("123");
		assertThat
			(
				"echo service response",
				echoService.echo("aB"),
				equalTo("123:aB")
			);
	}
}