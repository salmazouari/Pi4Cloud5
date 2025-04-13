package org.example.backendspring.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.example.backendspring.Entities.Seat;
import org.example.backendspring.Services.ISeatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seats")
@AllArgsConstructor
@Tag(name = "Seat", description = "Seat reservation management APIs")
public class SeatController {

    private final ISeatService seatService;

    @Operation(summary = "Create or update a seat")

    @PostMapping
    public Seat createOrUpdateSeat(@RequestBody Seat seat) {
        Optional<Seat> existingSeat = seatService.getSeatById(seat.getId());
        if (existingSeat.isPresent()) {
            // Update existing seat if it exists
            return seatService.updateSeat(seat.getId(), seat);
        } else {
            // Create a new seat if it doesn't exist
            return seatService.createSeat(seat);
        }
    }
    @PostMapping("/add/{eventID}")
    public void addSeat(@PathVariable Long eventID, @RequestBody Seat seat) {
        try {
            seatService.addSeatForEvent(seat, eventID);
        } catch (OptimisticLockException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Seat was modified by another transaction. Please refresh and try again.",
                    e
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error adding seat: " + e.getMessage(),
                    e
            );
        }
    }


    @Operation(summary = "Get all seats")
    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @Operation(summary = "Get a seat by ID")
    @GetMapping("/{id}")
    public Optional<Seat> getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }



    @Operation(summary = "Delete a seat")
    @DeleteMapping("/{id}")
    public void deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
    }
}
