import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventService } from '../services/event-services/event.service';
import { UserService } from '../user.service';
import { Event } from '../models/events.model';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {
  showEventsList = false;
  currentUserId: number | undefined;

  newEvent: Event = {
    title: '',
    description: '',
    location: '',
    startTime: '',
    endTime: '',
    maxAttendees: 0,
    hostedBy: '',
    user1: undefined
  };

  constructor(
    private eventService: EventService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    const currentUser = this.userService.getCurrentUser();
    this.currentUserId = currentUser?.userId;
    
    this.route.queryParams.subscribe(params => {
      if (params['eventID']) {
        this.newEvent = {
          eventID: +params['eventID'],
          title: params['title'] || '',
          description: params['description'] || '',
          location: params['location'] || '',
          startTime: params['startTime'] || '',
          endTime: params['endTime'] || '',
          maxAttendees: +params['maxAttendees'] || 0,
          hostedBy: params['hostedBy'] || '',
          user1: this.currentUserId
        };
      }
    });
  }

  onSubmit(): void {
    if (!this.currentUserId) {
      alert('User not authenticated');
      return;
    }

    // Ensure user1 is set to current user
    this.newEvent.user1 = this.currentUserId;

    const operation = this.newEvent.eventID 
      ? this.eventService.updateEvent(this.newEvent)
      : this.eventService.createEvent(this.newEvent);

    operation.subscribe({
      next: () => {
        alert(`Event ${this.newEvent.eventID ? 'updated' : 'created'} successfully!`);
        this.resetForm();
      },
      error: (err) => {
        console.error('Error processing event', err);
        alert(`Failed to ${this.newEvent.eventID ? 'update' : 'create'} event.`);
      }
    });
  }

  resetForm(): void {
    this.newEvent = {
      eventID: undefined,
      title: '',
      description: '',
      location: '',
      startTime: '',
      endTime: '',
      maxAttendees: 0,
      hostedBy: '',
      user1: this.currentUserId
    };
  }

  toggleEventsList(): void {
    this.router.navigate(['/event-card']);
  }

  onJoinEvent(event: Event): void {
    if (event.eventID) {
      this.router.navigate(['/reservation', event.eventID]);
    }
  }
}