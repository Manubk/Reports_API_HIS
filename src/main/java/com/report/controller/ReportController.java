package com.report.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	
	@GetMapping("/report/excel")
	public ResponseEntity<HSSFWorkbook> generateCitizenExcel(){
		log.info("generateCitizenExcel");
		
	}
}
