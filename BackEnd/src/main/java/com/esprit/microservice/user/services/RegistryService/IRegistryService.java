package com.esprit.microservice.user.services.RegistryService;

import com.esprit.microservice.user.entities.Registry;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IRegistryService {

    Registry getRegistryById(Long id);
    void deleteRegistry(Long id);
    Registry updateRegistry(Long id, Registry registryDetails);
    List<Registry> getAllRegistries();
    Registry createRegistry(Registry registry);
}
