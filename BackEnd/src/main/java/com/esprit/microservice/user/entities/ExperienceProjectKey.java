package com.esprit.microservice.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ExperienceProjectKey implements Serializable {

    @Column(name = "experience_id")
    private Long experienceId;

    @Column(name = "project_id")
    private Long projectId;

}
