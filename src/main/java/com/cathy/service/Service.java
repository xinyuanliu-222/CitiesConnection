package com.cathy.service;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cathy.model.City;

/**
 * 
 * @author Cathy
 * Check if destination city is reachable from the origin city.
 * Visited all city to check whether relationship exist. 
 * To avoid duplicate computing, using set to store visited cities.
 */

public class Service {

	private static final Log log = LogFactory.getLog(Service.class);
	
	public Service() {
		
	}
	
	public static boolean checkCityConnection(City origin, City destination) {
		log.info("Origin: " + origin.getName() + " to Destination: " + destination.getName());
		if (origin.equals(destination)) return true;
		if (origin.getConnected().contains(destination)) return true;
		Set<City> visited = new HashSet<>(Collections.singleton(origin));
		Deque<City> stack = new ArrayDeque<>(origin.getConnected());
		while (!stack.isEmpty()) {
			City city = stack.getLast();
			if (city.equals(destination)) return true;
			if (!visited.contains(city)) {
				visited.add(city);
				stack.addAll(city.getConnected());
				stack.removeAll(visited);
				
				log.info("Visiting " + city.getName() + " with neighbours: " + city.printConnection());
			} else stack.removeAll(Collections.singleton(city));
		}
		return false;
	}
}
