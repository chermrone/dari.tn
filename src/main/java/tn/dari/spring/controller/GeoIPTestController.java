package tn.dari.spring.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.dto.GeoIP;
import tn.dari.spring.service.RawDBDemoGeoIPLocationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dari/IP")
public class GeoIPTestController {
	
	
	@Autowired
	RawDBDemoGeoIPLocationService locationService;


	@PostMapping("/GeoIPTest")
	public GeoIP getLocation(@RequestParam(value = "ipAddress", required = true) String ipAddress) throws Exception {

		return locationService.getLocation(ipAddress);
	}

}
