package com.esprit.microservice.user.restController;

import com.esprit.microservice.user.entities.JobApplication;
import com.esprit.microservice.user.entities.JobOffer;
import com.esprit.microservice.user.services.IJobApplicationService;
import com.esprit.microservice.user.services.JobOfferService;
import com.esprit.microservice.user.entities.User;
import com.esprit.microservice.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    @Autowired
    private IJobApplicationService jobApplicationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private JobOfferService jobOfferService;

    @PostMapping("/apply")
    public ResponseEntity<JobApplication> applyToJob(
            @RequestParam Long studentId,
            @RequestParam Long jobOfferId,
            @RequestParam(required = false) String cv,
            @RequestParam(required = false) String motivationLetter) {
        User student = userService.getUserById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        JobOffer jobOffer = jobOfferService.getJobOfferById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        JobApplication application = new JobApplication();
        application.setStudent(student);
        application.setJobOffer(jobOffer);
        application.setCv(cv);
        application.setMotivationLetter(motivationLetter);

        JobApplication savedApplication = jobApplicationService.applyToJob(application);
        return ResponseEntity.ok(savedApplication);
    }

    // Other methods...
}