package tn.dari.spring.controller.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateUtils {

	public static List<Date> getDatesBetweenUsingJava8(
			  LocalDate startDate, LocalDate endDate) { 
			
			    long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate); 
				List<LocalDate> list = IntStream.iterate(0, i -> i + 1)
			      .limit(numOfDaysBetween + 1)
			      .mapToObj(i -> startDate.plusDays(i))
			      .collect(Collectors.toList()); 
				
				ZoneId defaultZoneId = ZoneId.of("Africa/Tunis");
				
				List<Date> listDates = new ArrayList<>();
					for(LocalDate D : list){
						
						Date date = Date.from(D.atStartOfDay(defaultZoneId).toInstant());
						listDates.add(date);
						
					}
				
				
				
				return listDates;
				
				
			}
	
	
	
}
