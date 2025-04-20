package com.esprit.microservice.user.services;

import com.esprit.microservice.user.entities.JobApplication;
import com.esprit.microservice.user.repositories.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationServiceImp implements IJobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Override
    public JobApplication applyToJob(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public List<JobApplication> getApplicationsByJobOffer(Long jobOfferId) {
        return jobApplicationRepository.findByJobOfferId(jobOfferId);
    }

    @Override
    public List<JobApplication> getApplicationsByStudent(Long studentId) {
        return jobApplicationRepository.findByStudentUserId(studentId);
    }

    @Override
    public Optional<JobApplication> updateApplicationStatus(Long applicationId, String status) {
        Optional<JobApplication> application = jobApplicationRepository.findById(applicationId);
        application.ifPresent(app -> {
            app.setStatus(status);
            jobApplicationRepository.save(app);
        });
        return application;
    }
}