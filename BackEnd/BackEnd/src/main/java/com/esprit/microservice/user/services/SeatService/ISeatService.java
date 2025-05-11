package com.esprit.microservice.user.services.SeatService;

import com.esprit.microservice.user.entities.Seat;

import java.util.List;
import java.util.Optional;

public interface ISeatService {
    Seat createSeat(Seat seat);
    List<Seat> getAllSeats();
    Optional<Seat> getSeatById(Long id);
    Seat updateSeat(Long id, Seat seat);
    void deleteSeat(Long id);
    void addSeatForEvent(Seat seat, Long eventID);
}
