package tn.dari.spring.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyProfit {
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Africa/Tunis")
	Date date;
	Float profit;
}
