package com.esprit.microservice.user.services.RegistryService;

import lombok.AllArgsConstructor;
import com.esprit.microservice.user.entities.Registry;
import com.esprit.microservice.user.entities.Seat;
import com.esprit.microservice.user.entities.User;
import com.esprit.microservice.user.repositories.RegistryRepository;
import com.esprit.microservice.user.repositories.SeatRepository;
import com.esprit.microservice.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegistryService implements IRegistryService {

    private final RegistryRepository registryRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
@Override
public Registry createRegistry(Registry registry) {
    // 1. Validate and set user
    User user = userRepository.findById(registry.getUser().getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
    registry.setUser(user);



    // 6. Save the registry (cascade should handle seats)
    return registryRepository.save(registry);
}



    @Override
    public List<Registry> getAllRegistries() {
        return registryRepository.findAll();
    }

    @Override
    public Registry getRegistryById(Long id) {
        return registryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registry not found with id: " + id));
    }

    @Override
    public Registry updateRegistry(Long id, Registry registryDetails) {
        Registry registry = getRegistryById(id);
        registry.setName(registryDetails.getName());
        registry.setLastname(registryDetails.getLastname());
        registry.setEmail(registryDetails.getEmail());
        registry.setSchool(registryDetails.getSchool());

        // Update user if changed
        if (registryDetails.getUser() != null &&
                !registry.getUser().getUserId().equals(registryDetails.getUser().getUserId())) {
            User user = userRepository.findById(registryDetails.getUser().getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            registry.setUser(user);
        }

        return registryRepository.save(registry);
    }

    @Override
    public void deleteRegistry(Long id) {
        Registry registry = getRegistryById(id);

        // Free all seats associated with this registry
        if (registry.getSeats() != null) {
            for (Seat seat : registry.getSeats()) {
                seat.setIsBooked(false);
                seat.setRegistry(null);
                seatRepository.save(seat);
            }
        }

        registryRepository.delete(registry);
    }


}