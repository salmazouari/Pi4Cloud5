import { Component, OnInit } from '@angular/core';
import { EventService } from '../services/event-services/event.service';
import { Event } from '../models/events.model';
import { Router } from '@angular/router';  // Import Router for navigation

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.scss']
})
export class EventCardComponent implements OnInit {
  events: Event[] = [];

  constructor(private eventService: EventService, private router: Router) {}

  ngOnInit(): void {
    this.eventService.getAllEvents().subscribe({
      next: (data) => {
        this.events = data;
      },
      error: (err) => {
        console.error('Error fetching events', err);
      }
    });
  }

  // Delete Event
  onDeleteEvent(eventId: number | undefined): void {
    if (eventId === undefined) {
      console.error('Event ID is undefined');
      return;
    }

    if (confirm('Are you sure you want to delete this event?')) {
      this.eventService.deleteEvent(eventId).subscribe({
        next: () => {
          // Remove the deleted event from the UI
          this.events = this.events.filter(event => event.eventID !== eventId);
        },
        error: (err) => {
          console.error('Error deleting event', err);
        }
      });
    }
  }

  // Navigate to update form with event data
  onUpdateEvent(event: Event): void {
    this.router.navigate(['/events'], {
      queryParams: {
        id: event.eventID,
        title: event.title,
        description: event.description,
        location: event.location,
        startTime: event.startTime,
        endTime: event.endTime,
        maxAttendees: event.maxAttendees,
        hostedBy: event.hostedBy
      }
    });
  }
  
}
