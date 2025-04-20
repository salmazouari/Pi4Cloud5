package com.esprit.microservice.user.restController;

import com.esprit.microservice.user.entities.JobOffer;
import com.esprit.microservice.user.services.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/joboffers")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @PostMapping("/createoffer")
    public ResponseEntity<JobOffer>  createJobOffer(@RequestBody JobOffer jobOffer) {
        // Your service logic here
        return ResponseEntity.ok(jobOfferService.createJobOffer(jobOffer));
    }

// this is the end
    @GetMapping("/alloffers")
    public ResponseEntity<List<JobOffer>> getAllJobOffers() {
        return ResponseEntity.ok(jobOfferService.getAllJobOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable Long id) {
        return jobOfferService.getJobOfferById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable Long id, @RequestBody JobOffer jobOffer) {
        return ResponseEntity.ok(jobOfferService.updateJobOffer(id, jobOffer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
        jobOfferService.deleteJobOffer(id);
        return ResponseEntity.noContent().build();
    }
}