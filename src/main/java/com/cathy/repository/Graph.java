package com.cathy.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cathy.model.City;

/**
 * 
 * @author Cathy
 * Build a map from the text file. Make sure names are not case sensitive
 * Mapping a city name with its related city for adding neighbor to the city 
 */

@Component
public class Graph {

	private final Log log = LogFactory.getLog(getClass());
	
	private Map<String, City> map = new HashMap<>();
	
	@Value("${data.file:classpath:city.txt}")
	private String cities;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Map<String, City> getMap() {
		return map;
	}
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	private void read() throws IOException {
		log.info("Reading Data");
		
		Resource resource = resourceLoader.getResource(cities);
		
		InputStream input;
		
		if (!resource.exists()) input = new FileInputStream(new File(cities));
		else input = resource.getInputStream();
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(input);
		
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (StringUtils.isEmpty(line)) continue;
			
			log.info(line);
			
			String[] ss = line.split(",");
			String a = ss[0].trim().toLowerCase();
			String b = ss[1].trim().toLowerCase();
			
			if (!a.equals(b)) {
				City A = map.getOrDefault(a, City.build(a));
				City B = map.getOrDefault(b, City.build(b));
				
				A.addConnected(B);
				B.addConnected(A);
				
				map.put(A.getName(), A);
				map.put(B.getName(), B);
			}
		}
		log.info("City Maps: " + map);
	}
	
	public City getCity(String name) {
		return map.get(name);
	}
}
