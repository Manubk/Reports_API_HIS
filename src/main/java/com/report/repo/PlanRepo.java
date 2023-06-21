package com.report.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.report.entity.Plan;

@Repository
public interface PlanRepo extends JpaRepository<Plan, Serializable> {
	
	@Query("SELECT DISTINCT(planName) from Plan")
	public List<String> findUniquePlanNames();
}
