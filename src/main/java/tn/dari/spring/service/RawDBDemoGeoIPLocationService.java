package tn.dari.spring.service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import tn.dari.spring.dto.GeoIP;

@Service
public class RawDBDemoGeoIPLocationService {

	private static final Logger log = LoggerFactory.getLogger(RawDBDemoGeoIPLocationService.class);
	private DatabaseReader dbReader;
	public String dbLocation = "src/main/resources/GeoLite2-City.mmdb";

	/*
	 * public RawDBDemoGeoIPLocationService() throws IOException { File database
	 * = new File(dbLocation); dbReader = new
	 * DatabaseReader.Builder(database).build(); }
	 */

	public GeoIP getLocation(String ip) throws IOException, GeoIp2Exception {
		try {
			File database = new File(dbLocation);
			dbReader = new DatabaseReader.Builder(database).build();
		} catch (IOException e) {
			log.info("IOException");
		}
		InetAddress ipAddress = InetAddress.getByName(ip);
		CityResponse response = dbReader.city(ipAddress);

		String cityName = response.getCity().getName();
		String latitude = response.getLocation().getLatitude().toString();
		String longitude = response.getLocation().getLongitude().toString();
		return new GeoIP(ip, cityName, latitude, longitude);
	}

}
