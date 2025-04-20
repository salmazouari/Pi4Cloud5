package com.esprit.microservice.user.entities;

import com.esprit.microservice.user.entities.User;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "job_offer_id", nullable = false)
    private com.esprit.microservice.user.entities.JobOffer jobOffer;

    private String status; // PENDING, ACCEPTED, REJECTED
    private LocalDate applicationDate;

    @Lob
    private String cv; // Optional CV content or file path

    @Lob
    private String motivationLetter; // Optional motivation letter content

    public JobApplication() {
        this.status = "PENDING";
        this.applicationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public com.esprit.microservice.user.entities.JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(com.esprit.microservice.user.entities.JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getMotivationLetter() {
        return motivationLetter;
    }

    public void setMotivationLetter(String motivationLetter) {
        this.motivationLetter = motivationLetter;
    }


    // ...
}
