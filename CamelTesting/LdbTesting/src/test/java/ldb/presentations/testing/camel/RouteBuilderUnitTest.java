package ldb.presentations.testing.camel;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "test-context.xml" })
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class RouteBuilderUnitTest
{
	@Resource
	private RouteBuilder camelRouteBuilder;
	
	@Resource
	private CamelContext camelContext;
	
	@Test
	public void route()
	{
		camelContext.createProducerTemplate()
			.sendBody("jms:queue:echo.request", "test message");
	}
}