package com.report.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.report.entity.ApplicationRegistration;

@Repository
public interface ApplicationRegistrationRepo extends JpaRepository<ApplicationRegistration, Long> {
}
