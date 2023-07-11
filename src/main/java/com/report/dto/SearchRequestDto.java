package com.report.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SearchRequestDto {
	
	private String planName;
	
	private String status;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String ssn;
	
}
