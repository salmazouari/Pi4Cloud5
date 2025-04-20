package com.esprit.microservice.user.repositories;

import com.esprit.microservice.user.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobOfferId(Long jobOfferId);
    List<JobApplication> findByStudentUserId(Long userId);
}