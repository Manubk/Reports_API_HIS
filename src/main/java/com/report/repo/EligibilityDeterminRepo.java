package com.report.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.report.entity.EligibilityDeterminEntity;

@Repository
public interface EligibilityDeterminRepo  extends JpaRepository<EligibilityDeterminEntity, Long>{

	@Query("SELECT DISTINCE(planStatus) from ELigibilityDeterminiEntity")
	public List<String> findUniquePlanStatus();
}
