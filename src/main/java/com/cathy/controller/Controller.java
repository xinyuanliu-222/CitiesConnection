package com.cathy.controller;

import java.util.Objects;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cathy.model.City;
import com.cathy.repository.Graph;
import com.cathy.service.Service;

import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Cathy
 * Rest controller for checking whether city1 is connected to city2 in the url params
 *
 */

@RestController
public class Controller {
	
	@Autowired
	Graph graph;
	
	@GetMapping(value = "/connected", produces = "text/plain")
	public String isConnected(@ApiParam(name = "origin", value = "origin city name", required = true) @RequestParam String origin, @ApiParam(name = "destination", value = "destination city name", required = true) @RequestParam String destination) {
		City originCity = graph.getCity(origin.toLowerCase());
		City destCity = graph.getCity(destination.toLowerCase());
		
		Objects.requireNonNull(originCity);
		Objects.requireNonNull(destCity);
		
		if (Service.checkCityConnection(originCity, destCity)) return "yes";
		else return "no";
	}
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String Error() {
		return ("Origin or Destination does not exist or invalid");
	}
}
