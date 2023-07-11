package com.report.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class UniqueSearchValues {

	private List<String> uniquePlans;
	
	private List<String> uniqueStatus;
	
	private List<LocalDate> uniqueStartDates;
	
	private List<LocalDate> uniqueEndDates;
}
