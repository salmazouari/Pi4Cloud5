package com.esprit.microservice.user.restController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.esprit.microservice.user.entities.Registry;
import com.esprit.microservice.user.services.RegistryService.IRegistryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registries")
@AllArgsConstructor
@Tag(name = "Registry", description = "Student registry management APIs")
public class RegistryController {

    private final IRegistryService registryService;

    @Operation(summary = "Create a new student registry")
    @PostMapping
    public Registry createRegistry(@RequestBody Registry registry) {
        return registryService.createRegistry(registry);
    }

    @Operation(summary = "Get all student registries")
    @GetMapping
    public List<Registry> getAllRegistries() {
        return registryService.getAllRegistries();
    }

    @Operation(summary = "Get a registry by ID")
    @GetMapping("/{id}")
    public Optional<Registry> getRegistryById(@PathVariable Long id) {
        return registryService.getRegistryById(id);
    }

    @Operation(summary = "Update a student registry")
    @PutMapping("/{id}")
    public Registry updateRegistry(@PathVariable Long id, @RequestBody Registry registry) {
        return registryService.updateRegistry(id, registry);
    }

    @Operation(summary = "Delete a student registry")
    @DeleteMapping("/{id}")
    public void deleteRegistry(@PathVariable Long id) {
        registryService.deleteRegistry(id);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex) {
        return ex.getMessage();
    }

}
