package ldb.presentations.testing.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

public class CamelRouteBuilder extends RouteBuilder
{
	private static final String ECHO_BLACKBOX_START = "direct:echo.database";

	private RouteDefinition setEchoRequest(RouteDefinition routeDef)
	{
		// A little trickery to do the same thing regardless of input endpoint!
		return routeDef.setHeader("theRequest", body())
			//.setHeader("theId", constant(null))
			.setBody(constant(null));
	}

	@Override
	public void configure() throws Exception
	{
		setEchoRequest(from("jms:queue:echo.request"))
			.to(ECHO_BLACKBOX_START)
			.to("jms:queue:echo.reply");
		
		from(ECHO_BLACKBOX_START)
			//.transacted()
			//.rollback()
//.to("log:echo-routing?showAll=true")
//.to("sql:SELECT COUNT(*) FROM echo")
//.to("log:echo-routing")
			.to("sql:{{echo.sql.create}}")
//.to("sql:SELECT COUNT(*) FROM echo")
//.to("log:echo-routing")
//.to("sql:ROLLBACK;")
//.to("sql:SELECT COUNT(*) FROM echo")
.to("log:echo-routing")
			//.to("echoService")
			//.to("sql:{{echo.sql.update.addResponse}}")
			;
	}
}