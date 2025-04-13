package org.example.backendspring.Services;

import lombok.AllArgsConstructor;
import org.example.backendspring.Entities.Registry;
import org.example.backendspring.Entities.Seat;
import org.example.backendspring.Repositories.RegistryRepository;
import org.example.backendspring.Repositories.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RegistryService implements IRegistryService {

    private final RegistryRepository registryRepository;
    private final SeatRepository seatRepository;

    @Override

    public Registry createRegistry(Registry registry) {
        // Check if seats are provided in the request
        if (registry.getSeats() != null && !registry.getSeats().isEmpty()) {
            Set<Seat> seats = new HashSet<>();
            // Iterate over seat IDs and fetch Seat entities from the database
            for (Seat seat : registry.getSeats()) {
                if (seat.getId() == null) {
                    throw new IllegalArgumentException("Each seat must have a valid ID.");
                }
                // Retrieve the seat entity from the database using the provided ID
                Seat existingSeat = seatRepository.findById(seat.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Seat not found for ID: " + seat.getId()));

                seats.add(existingSeat);  // Add the found seat to the seats set
            }

            registry.setSeats(seats);  // Set the seats in the registry entity
        }

        // Save the registry entity (along with its associated seats due to cascading)
        return registryRepository.save(registry);
    }



    @Override
    public List<Registry> getAllRegistries() {
        return registryRepository.findAll();
    }

    @Override
    public Optional<Registry> getRegistryById(Long id) {
        return registryRepository.findById(id);
    }

    @Override
    public Registry updateRegistry(Long id, Registry registry) {
        if (registryRepository.existsById(id)) {
            registry.setId(id);
            return registryRepository.save(registry);
        }
        return null; // Or throw an exception
    }

    @Override
    public void deleteRegistry(Long id) {
        registryRepository.deleteById(id);
    }
}
