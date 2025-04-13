package com.esprit.microservice.user.services.EventService;

import com.esprit.microservice.user.entities.Events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IEventService {
    Events createEvent(Events event);
    List<Events> getAllEvents();
    Optional<Events> getEventById(Long id);
    Events updateEvent(Long id, Events event);
    void deleteEvent(Long id);
    String getEventTitle(Long id);       // Fetches event title by ID
    String getEventLocation(Long id);
    LocalDateTime getEventEndTime(Long id);
    LocalDateTime getEventStartTime(Long id);
}
