package ldb.presentations.testing.camel;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import javax.annotation.Resource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
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
				"classpath:META-INF/context/echo-context.xml",
				"RouteBuilder-inmemory-context.xml"
			}
	)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class RouteBuilderIntTest
{
	@Rule
	public TestName testName = new TestName();
	
	@Resource
	private RouteBuilder camelRouteBuilder;
	
	@Resource
	private ModelCamelContext camelContext;
	
	@Resource
	private JdbcOperations jdbcOperations;
	
	@After
	public void tearDown() throws Exception
	{
		if (camelContext != null)
			camelContext.stop();
		
		if (jdbcOperations != null)
			jdbcOperations.update("SHUTDOWN");
	}
	
	@Test
	public void success() throws InterruptedException
	{
		String messageBody = testName.getMethodName() + "method";
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
				notNullValue()
			);
		
		assertThat
			(
				"response and request message comparison",
				jdbcOperations.queryForObject("SELECT response FROM echo", String.class),
				endsWith(":" + messageBody)
			);
	}
}