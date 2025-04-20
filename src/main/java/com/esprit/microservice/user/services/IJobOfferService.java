package com.esprit.microservice.user.services;

import com.esprit.microservice.user.entities.JobOffer;

import java.util.List;
import java.util.Optional;

public interface IJobOfferService {
    JobOffer createJobOffer(JobOffer jobOffer);
    List<JobOffer> getAllJobOffers();
    Optional<JobOffer> getJobOfferById(Long id);
    JobOffer updateJobOffer(Long id, JobOffer updatedJobOffer);
    void deleteJobOffer(Long id);
}
