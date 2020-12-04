package com.cathy;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.cathy.model.City;

/**
 * 
 * @author Cathy
 * Unit test for City model
 */

public class CityTest {

	@Test
	public void create() {
		City city = new City("Boston");
		Assert.assertEquals("boston", city.getName());
	}
	
	@Test
	public void createWithConnected() {
		City city = City.build("New York");
		city.addConnected(City.build("Stamford"));
		city.addConnected(City.build("LongIsland"));
		
		Set<City> connected = city.getConnected();
		Assert.assertEquals(2, connected.size());
		Assert.assertTrue(connected.contains(City.build("Stamford")));
	}
	
	@Test
	public void testWithDuplicates() {
		City city = City.build("Cloumbus");
		city.addConnected(City.build("Celevland"))
			.addConnected(City.build("Celevland"))
			.addConnected(City.build("Celevland"));
		Assert.assertEquals(1, city.getConnected().size());
	}
}
