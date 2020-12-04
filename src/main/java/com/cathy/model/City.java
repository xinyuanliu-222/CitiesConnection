package com.cathy.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Objects;

/**
 * 
 * @author Cathy
 * City class represent city name and its neighbor cities
 * city name is not case sensitive for computing
 */

public class City {

	private String name;
	
	private Set<City> connected = new HashSet<>(); //avoid duplicate
	
	public City() {
		
	}

	public City(String name) {
		super();
		this.name = name.trim().toLowerCase();
	}

	//avoid to write new every time when creating city object
	public static City build(String name) {
		return new City(name);
	}
	
	public City addConnected(City conn) {
		connected.add(conn);
		return this;
	}
	
	public String printConnection() {
		return connected.stream().map(City::getName).collect(Collectors.joining(","));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<City> getConnected() {
		return connected;
	}

	public void setConnected(Set<City> connected) {
		this.connected = connected;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof City)) return false;
		City city = (City) o;
		return Objects.equal(name, city.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

}
