import { Component, OnInit } from '@angular/core';
import { Event } from '../models/events.model';  // Adjust the path to the model
import { EventService } from '../services/event-services/event.service';  // Import the EventService
import { Router } from '@angular/router';  // Import Router to navigate

@Component({
  selector: 'app-join-event',
  templateUrl: './join-event.component.html',
  styleUrls: ['./join-event.component.scss']
})
export class JoinEventComponent implements OnInit {
  events: Event[] = [];  // Define the type as an array of Event

  constructor(
    private eventService: EventService,  // Inject EventService
    private router: Router  // Inject Router for navigation
  ) {}

  ngOnInit(): void {
    this.eventService.getAllEvents().subscribe({
      next: (data) => {
        this.events = data;  // Assign the fetched events to the 'events' array
      },
      error: (err) => {
        console.error('Error fetching events', err);  // Handle errors if any
      }
    });
  }

  onJoinEvent(event: Event): void {
    this.router.navigate(['/reservation', event.eventID], {
      state: { maxAttendees: event.maxAttendees }
    });
  }
  
}
