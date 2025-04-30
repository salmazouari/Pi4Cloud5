package com.esprit.microservice.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private Set<ExperienceProject> experienceProjects;


    @Column
    private String name;


    @Column
    private String description;
}
