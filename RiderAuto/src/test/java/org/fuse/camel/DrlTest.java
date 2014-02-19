package org.fuse.camel;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "applicationContext.xml" })
public class DrlTest {

	@Resource
	private CamelContext camelContext;
	

	@Test
	public void test() {
		
		try {
			camelContext.start();
			
			camelContext.createProducerTemplate()
			.sendBody("http://0.0.0.0:8888/placeorder", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><order name=\"motorDRL7777\" amount=\"1\"/>");
			
			camelContext.createProducerTemplate()
			.sendBody("file:target/placeorder", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><order name=\"motorDRL8888\" amount=\"1\"/>");
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
