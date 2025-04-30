package com.esprit.microservice.user.repositories;

import com.esprit.microservice.user.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
