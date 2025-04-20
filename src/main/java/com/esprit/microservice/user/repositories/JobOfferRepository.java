package com.esprit.microservice.user.repositories;

import com.esprit.microservice.user.entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByRecruiterUserId(Long userId);
}
