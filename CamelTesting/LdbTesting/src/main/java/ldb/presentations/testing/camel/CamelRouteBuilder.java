package ldb.presentations.testing.camel;

import javax.annotation.Resource;

import ldb.presentations.testing.echo.service.EchoService;

import org.apache.camel.builder.RouteBuilder;

public class CamelRouteBuilder extends RouteBuilder
{
	public static final String ECHO_JMS_REQUEST_ROUTE = "echo.jms.request";
	
	public static final String ECHO_SERVICE_ROUTE = "echo.service";
	
	//TODO Package private? how to make accessible by test in non-implementation manner?
	public static final String ECHO_SQL_CREATE_ID = "echo.sql.create";
	
	private static final String ECHO_BLACKBOX_START = "direct:echo.database";
	
	@Resource
	private EchoService echoService;

	@Override
	public void configure() throws Exception
	{
		from("jms:queue:echo.request?transacted=true")
			.routeId(ECHO_JMS_REQUEST_ROUTE)
			.to(ECHO_BLACKBOX_START)		
				.id("echo.blackbox")
			.to("jms:queue:echo.reply")
				.id("echo.jms.reply");
		
		from(ECHO_BLACKBOX_START)
			.routeId(ECHO_SERVICE_ROUTE)
			.setHeader("theRequest", body())
				.id("header.set.request")
			.bean(echoService, "echo")
				.id("echo.service")
			.setHeader("theResponse", body())
				.id("header.set.response")
			.to("sql:{{echo.sql.create}}")
				.id(ECHO_SQL_CREATE_ID)
			.setBody(header("theResponse"));
	}
}