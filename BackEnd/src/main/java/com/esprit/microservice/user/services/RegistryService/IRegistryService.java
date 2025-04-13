package com.esprit.microservice.user.services.RegistryService;

import com.esprit.microservice.user.entities.Registry;

import java.util.List;
import java.util.Optional;

public interface IRegistryService {
    Registry createRegistry(Registry registry);
    List<Registry> getAllRegistries();
    Optional<Registry> getRegistryById(Long id);
    Registry updateRegistry(Long id, Registry registry);
    void deleteRegistry(Long id);
}
