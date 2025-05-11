package com.esprit.microservice.user.restController;

import com.esprit.microservice.user.entities.Events;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.esprit.microservice.user.entities.Registry;
import com.esprit.microservice.user.services.RegistryService.IRegistryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Registry")
@AllArgsConstructor
@Tag(name = "Registry", description = "Student registry management APIs")
@CrossOrigin("http://localhost:4200")
public class RegistryController {

    private final IRegistryService registryService;

    @Operation(summary = "Create a new student registry")
    @PostMapping("/registries")

    public ResponseEntity<Registry>createRegistry(@RequestBody Registry registry) {

           registryService.createRegistry(registry);
            return ResponseEntity.ok(registry);

    }

    @Operation(summary = "Get all student registries")
    @GetMapping
    public List<Registry> getAllRegistries() {
        return registryService.getAllRegistries();
    }

    @Operation(summary = "Get a specific registry by ID")
    @GetMapping("/{id}")
    public Registry getRegistryById(@PathVariable Long id) {
        return registryService.getRegistryById(id);
    }

    @Operation(summary = "Update a student registry")
    @PutMapping("/{id}")
    public Registry updateRegistry(@PathVariable Long id, @RequestBody Registry registry) {
        try {
            return registryService.updateRegistry(id, registry);
        } catch (IllegalArgumentException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(summary = "Delete a student registry")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRegistry(@PathVariable Long id) {
        try {
            registryService.deleteRegistry(id);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException ex) {
        return ex.getMessage();
    }
}