package com.report.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.report.constants.AppConstants;
import com.report.dto.SearchRequestDto;
import com.report.dto.SearchResponseDto;
import com.report.dto.UniqueSearchValues;
import com.report.serviceinterface.IReportService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
public class ReportController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	private IReportService reportService;
	
	
	@PostMapping("/eligibilityreports")
	public ResponseEntity<List<SearchResponseDto>> getSearchResult(@RequestBody SearchRequestDto searchRequestDto){
	log.info("getSearchResult");
	
	List<SearchResponseDto> searchResult = reportService.findBySearch(searchRequestDto);
	
	return new ResponseEntity<List<SearchResponseDto>>(searchResult,HttpStatus.OK);
	
	}
	
	@GetMapping("/searchvalues")
	public ResponseEntity<UniqueSearchValues> findUniqueSearchValue(){
		log.info("findUniqueSearchValue");
		
		UniqueSearchValues uniqueSearchValues = reportService.getUniqueSearchValues();
		
		return new ResponseEntity<UniqueSearchValues>(uniqueSearchValues,HttpStatus.OK);
	}
	
	
	@GetMapping("/report/excel")
	public ResponseEntity<byte[]> generateCitizenExcel() throws IOException{
		log.info("generateCitizenExcel");
		
		HSSFWorkbook generateCitizenReport = reportService.generateCitizenReport();
		OutputStream out = new FileOutputStream("src\\main\\resources\\reports\\report.xls");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		
		generateCitizenReport.write(bytes);
		out.write(bytes.toByteArray());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, ":attachment;filename="+AppConstants.REPORT_EXCEL_FILE_NAME)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
				.body(bytes.toByteArray());
		
	}
}
