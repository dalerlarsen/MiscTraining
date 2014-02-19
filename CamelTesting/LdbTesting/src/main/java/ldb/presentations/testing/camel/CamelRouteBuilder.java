package ldb.presentations.testing.camel;

import org.apache.camel.builder.RouteBuilder;

public class CamelRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		from("jms:queue:echo.request")
			.to("jms:queue:echo.reply");
		
		/*
		 	from(jms:queue:request)
		 		.to(direct:echosvc)
		 		.to(jms:queue:reply);
		 		
		 	from(webservice:requeust)
		 		.to(direct:echosvc)
		 		.to(webservice:reply);
		 		
		 	from(direct:echosvc)
		 		.to(jdbc:insert)
		 		.bean(echoServiceBean)
		 		.to(jdbc:update);
		*/
	}
}