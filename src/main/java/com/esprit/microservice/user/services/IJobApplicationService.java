package com.esprit.microservice.user.services;

import com.esprit.microservice.user.entities.JobApplication;

import java.util.List;
import java.util.Optional;

public interface IJobApplicationService {
    JobApplication applyToJob(JobApplication jobApplication);
    List<JobApplication> getApplicationsByJobOffer(Long jobOfferId);
    List<JobApplication> getApplicationsByStudent(Long studentId);
    Optional<JobApplication> updateApplicationStatus(Long applicationId, String status);
}