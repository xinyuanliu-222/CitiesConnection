package com.cathy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cathy.model.City;
import com.cathy.repository.Graph;
import com.cathy.service.Service;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CitiestConnectionApplicationTests {

	@Autowired
	Graph graph;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void contextLoads() {
		Assert.assertFalse(graph.getMap().isEmpty());
	}

	@Test
	public void sameCity() {
		City city = City.build("Las Vegas");
		Assert.assertTrue(Service.checkCityConnection(city, city));
	}
	
	@Test
	public void nearConnected() {
		City a = graph.getCity("boston");
		City b= graph.getCity("newark");
		Assert.assertTrue(Service.checkCityConnection(a, b));
	}
	
	@Test
	public void distantConnect() {
		City a = graph.getCity("boston");
		City b= graph.getCity("philadelphia");
		Assert.assertTrue(Service.checkCityConnection(a, b));
	}
	
	@Test
	public void cannotConnect() {
		City a= graph.getCity("philadelphia");
		City b= graph.getCity("albany");
		Assert.assertFalse(Service.checkCityConnection(a, b));
	}
	
	@Test
	public void restConnected() {
		Map<String, String> map = new HashMap<>();
		map.put("origin", "Boston");
		map.put("destination", "Newark");
		
		String s = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, map);
		Assert.assertEquals("yes", s);
	}
	
	@Test
	public void restDistinctConnected() {
		Map<String, String> map = new HashMap<>();
		map.put("origin", "Boston");
		map.put("destination", "Philadelphia");
		
		String s = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, map);
		Assert.assertEquals("yes", s);
	}
	
	@Test
	public void restNotConnected() {
		Map<String, String> map = new HashMap<>();
		map.put("origin", "Philadelphia");
		map.put("destination", "Albany");
		
		String s = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, map);
		Assert.assertEquals("no", s);
	}
	
	@Test
	public void badRequest() {
        ResponseEntity<String> response = restTemplate.exchange("/connected?origin=none&destination=none", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

}
