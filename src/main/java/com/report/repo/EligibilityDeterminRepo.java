package com.report.repo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.report.entity.EligibilityDeterminEntity;

@Repository
public interface EligibilityDeterminRepo  extends JpaRepository<EligibilityDeterminEntity, Long>{

	@Query("SELECT DISTINCT e.planStatus from EligibilityDeterminEntity e")
	public List<String> findUniquePlanStatus();
	
	@Query("SELECT DISTINCT(planStartDate) from EligibilityDeterminEntity")
	public List<LocalDate> findUniqueStartDate();
	
	@Query("SELECT DISTINCT(planEndDate) from EligibilityDeterminEntity")
	public List<LocalDate> findUniqueEndDate();
}
