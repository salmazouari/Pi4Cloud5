package com.esprit.microservice.user.restController;

import com.esprit.microservice.user.entities.Events;
import com.esprit.microservice.user.services.EventService.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@CrossOrigin("http://localhost:4200")
public class EventController {

    @Autowired
    private IEventService eventService;

    @PostMapping
    public ResponseEntity<Events> createEvent(@RequestBody Events event) {
        eventService.createEvent(event);
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public List<Events> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Optional<Events> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PutMapping("/{id}")
    public Events updateEvent(@PathVariable Long id, @RequestBody Events event) {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
    @GetMapping("/events/{id}/title")
    public String getEventTitle(@PathVariable Long id) {
        return eventService.getEventTitle(id);
    }

    // Endpoint to get the event location
    @GetMapping("/events/{id}/location")
    public String getEventLocation(@PathVariable Long id) {
        return eventService.getEventLocation(id);
    }
    @GetMapping("/{id}/starttime")
    public ResponseEntity<String> getEventStartTime(@PathVariable Long id) {
        LocalDateTime startTime = eventService.getEventStartTime(id);
        if (startTime != null) {
            String formattedStartTime = startTime.toString(); // or you can use a custom format if needed
            return ResponseEntity.ok(formattedStartTime);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/endtime")
    public ResponseEntity<String> getEventEndTime(@PathVariable Long id) {
        LocalDateTime endTime = eventService.getEventEndTime(id);
        if (endTime != null) {
            String formattedEndTime = endTime.toString(); // or a custom format
            return ResponseEntity.ok(formattedEndTime);
        }
        return ResponseEntity.notFound().build();
    }

}
