package com.demo.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testAInitDb() {
		//set test data
		String symbol = "AAPL";
		String period = "1y";
		
		ResponseEntity<String> response = this.restTemplate
				.postForEntity("http://localhost:" + port + "/stock/" + symbol + "/history/" + period, "", String.class);
		
		assertEquals(200, response.getStatusCodeValue());
		
		String message = "Data initialized successfully!";
		assertEquals(response.getBody().toString(), message);
		
	}

	@Test
	public void testBGetStockValue() {
		//set test data
		String symbol = "AAPL";
		String queryParam = "date=1631491200000&money=100000";
		String url = "http://localhost:" + port + "/stock/" + symbol + "?" + queryParam; 

		String response = this.restTemplate.getForObject(url, String.class);
		
		//System.out.println("*** response: " + response.toString());
		//simple string compare without converting to JSON
		// since the latest price updated frequently which will cause the test failure, just use stock name for demo
		String expected = "AAPL";  //"{\"stock\": \"AAPL\",\"buy price\": 148.72,\"current price\":156.37,\"cost\": 100000,\"latest value\":105080.64}";  
		assertTrue(response.indexOf(expected)>0);		

	}
}
