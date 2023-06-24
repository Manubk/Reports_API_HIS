package com.report.serviceinterface;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.report.dto.SearchRequestDto;
import com.report.dto.SearchResponseDto;

@Service
public interface IReportService {
	
	public List<String> findPlanNames();
	
	public List<String> findPlanStatus();
	
	public List<SearchResponseDto> findBySearch(SearchRequestDto requestDto);
	
	public HSSFWorkbook generateCitizenReport();
	
	public HSSFWorkbook generateCitizenReport(SearchRequestDto requestDto);
	

}
