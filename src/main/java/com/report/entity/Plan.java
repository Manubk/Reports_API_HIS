package com.report.entity;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PLAN_MASTER")
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PLAN_ID")
	public  Integer PlanId;
	
	@Column(name = "PLAN_NAME")
	public String planName;
	
	@Column(name = "PLAN_START_DATE")
	public LocalDate planStartDate;
	
	@Column(name = "PLAN_END_DATE")
	public LocalDate planEndDate;
	
	@Column(name = "CATEGORY_ID")
	public Integer categoryId;
	
	@Column(name = "ACTIVE_SW")
	public String activeSw;
}
