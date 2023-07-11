package com.report.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;
import org.hibernate.annotations.ColumnDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Font;
import com.report.dto.SearchRequestDto;
import com.report.dto.SearchResponseDto;
import com.report.dto.UniqueSearchValues;
import com.report.entity.ApplicationRegistration;
import com.report.entity.DcCase;
import com.report.entity.EligibilityDeterminEntity;
import com.report.entity.Plan;
import com.report.repo.ApplicationRegistrationRepo;
import com.report.repo.DcCaseRepo;
import com.report.repo.EligibilityDeterminRepo;
import com.report.repo.PlanRepo;
import com.report.serviceinterface.IReportService;

@Service
public class ReportServiceImpl implements IReportService {

	private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	private PlanRepo planRepo;

	@Autowired
	private EligibilityDeterminRepo eligibilityDeterminRepo;

	@Autowired
	private DcCaseRepo caseRepo;

	@Autowired
	private ApplicationRegistrationRepo applicationRegistrationRepo;



	@Override
	public List<SearchResponseDto> findBySearch(SearchRequestDto requestDto) {
		log.info("findBySearch");

		SearchResponseDto responseDto = new SearchResponseDto();

		EligibilityDeterminEntity queryBuilder = new EligibilityDeterminEntity();

		if (requestDto.getPlanName() != null && !requestDto.getPlanName().equals("")) {
			queryBuilder.setPlanName(requestDto.getPlanName());
		}

		if (requestDto.getStatus() != null && !requestDto.getStatus().equals("")) {
			queryBuilder.setPlanStatus(requestDto.getStatus());
		}

		if (requestDto.getStartDate() != null) {
			queryBuilder.setPlanStartDate(requestDto.getStartDate());
		}

		if (requestDto.getEndDate() != null) {
			queryBuilder.setPlanEndDate(requestDto.getEndDate());
		}

		// Dynamic query BUilding based on search or filter
		Example<EligibilityDeterminEntity> example = Example.of(queryBuilder);

		List<EligibilityDeterminEntity> eligbilityDetails = eligibilityDeterminRepo.findAll(example);

		List<SearchResponseDto> responses = new ArrayList<>();

		eligbilityDetails.forEach(details -> {
			Optional<DcCase> OCase = caseRepo.findById(details.getCaseNum());

			if (OCase.isPresent()) {
				Optional<ApplicationRegistration> OAppReg = applicationRegistrationRepo
						.findById(OCase.get().getAppId());

				if (OAppReg.isPresent()) {
					ApplicationRegistration appReg = OAppReg.get();

					SearchResponseDto response = new SearchResponseDto();
					response.setFullName(appReg.getFullName());
					response.setEmail(appReg.getEmail());
					response.setGender(appReg.getGender());
					response.setPhoneNo(appReg.getPhoneNo());
					response.setSsn(appReg.getSsn());

					responses.add(response);
				}

			}
		}

		);
		return responses;
	}

	@Override
	public HSSFWorkbook generateCitizenReport() {
		log.info("generateCitizenReport");

		List<EligibilityDeterminEntity> eligibilityDetails = eligibilityDeterminRepo.findAll();

		List<SearchResponseDto> responses = new ArrayList<>();
		
		

		HSSFWorkbook workBook = new HSSFWorkbook();

		HSSFSheet citizenSheet = workBook.createSheet("CitizenDetails");
		
		CellStyle headerCell = workBook.createCellStyle();
		 HSSFFont headerFont = workBook.createFont();
		 headerFont.setFontName(HSSFFont.FONT_ARIAL);
		 headerFont.setBold(true);
		 headerFont.setColor(HSSFColorPredefined.DARK_TEAL.getIndex());
		 headerCell.setFont(headerFont);
		 
		 
		HSSFRow headerRow = citizenSheet.createRow(0);

		headerRow.createCell(0).setCellValue("SL No");
		headerRow.createCell(1).setCellValue("Full Name");
		headerRow.createCell(2).setCellValue("Email");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("Phone No");
		headerRow.createCell(5).setCellValue("SSN");
		headerRow.setRowStyle(headerCell);
		int slNo = 1;

		for(EligibilityDeterminEntity details: eligibilityDetails) {

			Optional<DcCase> OCase = caseRepo.findById(details.getCaseNum());

			HSSFRow row = citizenSheet.createRow(slNo);
			row.createCell(0).setCellValue(slNo++);

			if (OCase.isPresent()) {
				Optional<ApplicationRegistration> OAppReg = applicationRegistrationRepo
						.findById(OCase.get().getAppId());

				if (OAppReg.isPresent()) {
					ApplicationRegistration appReg = OAppReg.get();

					row.createCell(1).setCellValue(appReg.getFullName());
					row.createCell(2).setCellValue(appReg.getEmail());
					row.createCell(3).setCellValue(appReg.getGender());
					row.createCell(4).setCellValue(appReg.getPhoneNo());
					row.createCell(5).setCellValue(appReg.getSsn());

				}

			}
		}

		return workBook;
	}

	@Override
	public HSSFWorkbook generateCitizenReport(SearchRequestDto requestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UniqueSearchValues getUniqueSearchValues() {
		log.info("getUniqueSearchValues");
		
		// should get unique plans
		List<String> uniquePlanNames = planRepo.findUniquePlanNames();
		
		
		//should get unique status
		List<String> uniqueStatus = eligibilityDeterminRepo.findUniquePlanStatus();
		
		
		//should get unique plan start dates
		List<LocalDate> uniqueStartDate = eligibilityDeterminRepo.findUniqueStartDate();
		
		//should get unique plan end dates
		List<LocalDate> uniqueEndDate = eligibilityDeterminRepo.findUniqueEndDate();
		
		UniqueSearchValues uniqueValues = new UniqueSearchValues();
		uniqueValues.setUniquePlans(uniquePlanNames);
		uniqueValues.setUniqueStatus(uniqueStatus);
		uniqueValues.setUniqueStartDates(uniqueStartDate);
		uniqueValues.setUniqueEndDates(uniqueEndDate);
		
		return uniqueValues;
	}

}
