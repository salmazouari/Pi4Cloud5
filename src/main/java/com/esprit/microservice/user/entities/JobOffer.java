package com.esprit.microservice.user.entities;

import com.esprit.microservice.user.entities.User;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class JobOffer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private String region;
    private String type; // e.g., Full-time, Part-time
    private String email;
    private String image;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    public JobOffer() {}

    public JobOffer(String title, String description, String location, String region, String type, String email, String image, User recruiter) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.region = region;
        this.type = type;
        this.email = email;
        this.image = image;
        this.recruiter = recruiter;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getRecruiter() {
        return null;
    }

    public void setRecruiter(User recruiter) {
        this.recruiter = recruiter;
    }
}