package com.report.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.report.entity.Plan;

@Repository
public interface PlanRepo extends JpaRepository<Plan, Serializable> {
	
//	@Query("SELECT DISTINCT PlanId , planName FROM Plan ")
//	public Map<Integer , String> findUniquePlanNames();
	
	@Query("SELECT DISTINCT planName FROM Plan")
	public List<String> findUniquePlanNames();
}
