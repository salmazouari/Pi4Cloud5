package com.esprit.microservice.user.services.EventService;

import com.esprit.microservice.user.entities.User;
import com.esprit.microservice.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import com.esprit.microservice.user.entities.Events;
import com.esprit.microservice.user.repositories.EventsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService implements IEventService {

    private final EventsRepository eventRepository;
    private final UserRepository userRepository;


    @Override
    public Events createEvent(Events event) {


        User user = userRepository.findById(event.getUser1().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.setUser1(user);
        eventRepository.save(event);

        return event;
    }


    @Override
    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Events> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Events updateEvent(Long id, Events event) {
        if (eventRepository.existsById(id)) {
            event.setEventID(id);
            return eventRepository.save(event);
        }
        return null; // Or throw an exception
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    public String getEventTitle(Long id) {
        Optional<Events> event = eventRepository.findById(id);
        return event.map(Events::getTitle).orElse("Event not found");
    }

    // Method to get the location of an event by ID
    public String getEventLocation(Long id) {
        Optional<Events> event = eventRepository.findById(id);
        return event.map(Events::getLocation).orElse("Location not available");
    }
    public LocalDateTime getEventStartTime(Long id) {
        Optional<Events> event = eventRepository.findById(id);
        return event.map(Events::getStartTime).orElse(null);
    }

    // Method to get the end time of an event by ID
    public LocalDateTime getEventEndTime(Long id) {
        Optional<Events> event = eventRepository.findById(id);
        return event.map(Events::getEndTime).orElse(null);
    }
}
