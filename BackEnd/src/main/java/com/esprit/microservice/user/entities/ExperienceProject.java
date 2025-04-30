package com.esprit.microservice.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class ExperienceProject {

    @EmbeddedId
    private ExperienceProjectKey experienceProjectKey;

    @ManyToOne
    @MapsId("experienceId")
    private Experience experience;

    @ManyToOne
    @MapsId("projectId")
    private Project project;

    @OneToMany(mappedBy = "experienceProject")
    private Set<Task> tasks;

}
