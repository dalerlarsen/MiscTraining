package ldb.presentations.testing.camel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import javax.annotation.Resource;

import ldb.presentations.testing.echo.service.EchoService;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.ToDefinition;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
	(
		locations =
			{
				"classpath:META-INF/context/camel-context.xml",
				"classpath:META-INF/context/sql-context.xml",
				"RouteBuilder-inmemory-context.xml",
				"RouteBuilderUnitTest-context.xml"
			}
	)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class RouteBuilderUnitTest
{
	@Rule
	public TestName testName = new TestName();
	
	@Resource
	private RouteBuilder camelRouteBuilder;
	
	@Resource
	private ModelCamelContext camelContext;
	
	@Resource
	private JdbcOperations jdbcOperations;
	
	@Resource
	private EchoService echoServiceMock;
	
	@After
	public void tearDown() throws Exception
	{
		if (camelContext != null)
			camelContext.stop();
		
		if (jdbcOperations != null)
			jdbcOperations.update("SHUTDOWN");
	}
	
	@Test
	public void errorInInsert() throws Exception
	{
		//TODO how to check where/what went boom?
		// Stop route, create bad SQL, then add route so that replaces good one
		camelContext.stopRoute(CamelRouteBuilder.ECHO_SERVICE_ROUTE);
		RouteDefinition routeDefinition = camelContext.getRouteDefinition(CamelRouteBuilder.ECHO_SERVICE_ROUTE);
		
		for (ProcessorDefinition<?> processorDefinition : routeDefinition.getOutputs())
		{
			if (CamelRouteBuilder.ECHO_SQL_CREATE_ID.equals(processorDefinition.getId()))
			{
				ToDefinition toDefinition = (ToDefinition) processorDefinition;
				String newUri = toDefinition.getUri().replace(":", ":X");
				toDefinition.setUri(newUri);
				break;
			}
		}
		
		camelContext.addRouteDefinition(routeDefinition);
		camelContext.startRoute(CamelRouteBuilder.ECHO_SERVICE_ROUTE);
		
		String messageBody = testName.getMethodName() + ":test message";
		camelContext.createProducerTemplate().sendBody("jms:queue:echo.request", messageBody);
	
		assertThat
			(
				"request message",
				(String) camelContext.createConsumerTemplate().receiveBody("jms:queue:echo.request", 2000L),
				equalTo(messageBody)
			);
		
		assertThat
			(
				"response message",
				(String) camelContext.createConsumerTemplate().receiveBody("jms:queue:echo.reply", 2000L),
				nullValue()
			);
		
		assertThat
			(
				"number of echo entries",
				jdbcOperations.queryForObject("SELECT COUNT(*) FROM echo", Long.class),
				equalTo(0L)
			);
	}
	
	@Test
	public void echoServiceError()
	{
		//TODO how to check where/what went boom?
		String messageBody = testName.getMethodName() + ":test message";
		when(echoServiceMock.echo(eq(messageBody))).thenThrow(new IllegalArgumentException());
		camelContext.createProducerTemplate().sendBody("jms:queue:echo.request", messageBody);
	
		assertThat
			(
				"request message",
				(String) camelContext.createConsumerTemplate().receiveBody("jms:queue:echo.request", 2000L),
				equalTo(messageBody)
			);
		
		assertThat
			(
				"response message",
				(String) camelContext.createConsumerTemplate().receiveBody("jms:queue:echo.reply", 2000L),
				nullValue()
			);
		
		assertThat
			(
				"number of echo entries",
				jdbcOperations.queryForObject("SELECT COUNT(*) FROM echo", Long.class),
				equalTo(0L)
			);
	}

	@Test
	public void success() throws InterruptedException
	{
		String messageBody = testName.getMethodName() + ":test message";
		when(echoServiceMock.echo(eq(messageBody))).thenReturn(messageBody);
		camelContext.createProducerTemplate().sendBody("jms:queue:echo.request", messageBody);
		
		assertThat
			(
				"request message",
				(String) camelContext.createConsumerTemplate().receiveBody("jms:queue:echo.request", 2000L),
				nullValue()
			);
	
		assertThat
			(
				"response message",
				(String) camelContext.createConsumerTemplate().receiveBody("jms:queue:echo.reply", 2000L),
				equalTo(messageBody)
			);
		
		assertThat
			(
				"number of echo entries",
				jdbcOperations.queryForObject
					(
						"SELECT COUNT(*) " +
							"FROM echo " +
							"WHERE request IS NOT NULL " +
							"AND response IS NOT NULL " +
							"AND request = response",
						Long.class
					),
				equalTo(1L)
			);
	}
}