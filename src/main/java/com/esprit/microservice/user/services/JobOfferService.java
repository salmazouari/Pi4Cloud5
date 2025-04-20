package com.esprit.microservice.user.services;

import com.esprit.microservice.user.entities.JobOffer;
import com.esprit.microservice.user.repositories.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobOfferService implements IJobOfferService{
    @Autowired
    private JobOfferRepository jobOfferRepository;

    public JobOffer createJobOffer(JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    public Optional<JobOffer> getJobOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }

    public JobOffer updateJobOffer(Long id, JobOffer updatedJobOffer) {
        return jobOfferRepository.findById(id).map(jobOffer -> {
            jobOffer.setTitle(updatedJobOffer.getTitle());
            jobOffer.setDescription(updatedJobOffer.getDescription());
            jobOffer.setLocation(updatedJobOffer.getLocation());
            jobOffer.setRegion(updatedJobOffer.getRegion());
            jobOffer.setType(updatedJobOffer.getType());
            jobOffer.setEmail(updatedJobOffer.getEmail());
            jobOffer.setImage(updatedJobOffer.getImage());
            return jobOfferRepository.save(jobOffer);
        }).orElseThrow(() -> new RuntimeException("JobOffer not found"));
    }

    public void deleteJobOffer(Long id) {
        jobOfferRepository.deleteById(id);
    }
}
