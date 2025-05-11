package com.esprit.microservice.user.services.SeatService;

import lombok.AllArgsConstructor;
import com.esprit.microservice.user.entities.Events;
import com.esprit.microservice.user.entities.Seat;
import com.esprit.microservice.user.repositories.EventsRepository;
import com.esprit.microservice.user.repositories.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SeatService implements ISeatService {

    private final SeatRepository seatRepository;
    private final EventsRepository eventsRepository;

    @Override
    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }
    @Override
    public void addSeatForEvent(Seat seat, Long eventID) {
        // Get event from the database by eventID
        Optional<Events> event = eventsRepository.findById(eventID);
        if (event.isPresent()) {
            seat.setEvent(event.get());  // Set the event for the seat
            seatRepository.save(seat);    // Save the seat
        } else {
            throw new RuntimeException("Event not found");
        }
    }


    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @Override
    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }

    @Override
    public Seat updateSeat(Long id, Seat seat) {
        if (seatRepository.existsById(id)) {
            seat.setId(id);
            return seatRepository.save(seat);
        }
        return null; // Or throw an exception
    }

    @Override
    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }

}
