package ldb.presentations.testing.camel;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
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
				"test-context.xml"
			}
	)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class RouteBuilderUnitTest
{
	@Resource
	private RouteBuilder camelRouteBuilder;
	
	@Resource
	private CamelContext camelContext;
	
	@After
	public void tearDown() throws Exception
	{
		if (camelContext != null)
			camelContext.stop();
	}
	
	@Test
	public void route() throws InterruptedException
	{
		camelContext.createProducerTemplate()
			.sendBody("jms:queue:echo.request", "test message");
		
System.err.println("XXX" +
		camelContext.createConsumerTemplate()
			.receiveBody("jms:queue:echo.reply", 10000L));
	}
}