package com.esprit.microservice.user.repositories;

import com.esprit.microservice.user.entities.Registry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistryRepository extends JpaRepository<Registry, Long> {
}
