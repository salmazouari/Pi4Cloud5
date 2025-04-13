import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventService } from '../services/event-services/event.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Registration } from '../models/registry.model';
import { UserService } from '../user.service';
import { SeatService } from '../services/event-services/seat-services/seat.service';
import { Seat } from '../models/seats.model';
import { Event } from '../models/events.model';
import { RegistrationService } from '../services/registration.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit {
  availableSeats: Seat[] = [];
  bookedSeats: Seat[] = [];
  selectedSeats: Seat[] = [];
  maxAttendees: number = 0;
  startTime: string = '';
  endTime: string = '';
  pricePerSeat: number = 15;
  EventID: number = 0;
  eventTitle: string | undefined = 'Loading...';
  eventLocation: string | undefined = 'Loading...';
  currentUserId: number | undefined;
  attendeeForm!: FormGroup;
  
  event: Event = {
    title: '',
    description: '',
    location: '',
    startTime: '',
    endTime: '',
    maxAttendees: 0,
    hostedBy: ''
  };
  
  constructor(
    private router: Router,
    private seatService: SeatService,
    private eventService: EventService, 
    private route: ActivatedRoute,
    private registrationService: RegistrationService,
    private fb: FormBuilder,
    private userService: UserService
  ) {
    const navigation = this.router.getCurrentNavigation();
    this.maxAttendees = navigation?.extras?.state?.['maxAttendees'] || 0;
    this.route.params.subscribe(params => {
      this.EventID = +params['id'];
      this.attendeeForm = this.fb.group({
        name: ['', Validators.required],
        lastname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        school: ['']
      });
    });
    this.loadSeats();
  }

  ngOnInit(): void {
    
    const currentUser = this.userService.getCurrentUser();
    this.currentUserId = currentUser?.userId;
    this.currentUserId = currentUser?.userId;
    this.loadEventDetails();
  }
  
  loadSeats(): void {
    this.availableSeats = Array.from({ length: this.maxAttendees }, (_, i) => ({
      id: i + 1,
      placement: `Seat-${i + 1}`,
      event: { EventID: this.EventID },
      isBooked: false,
      registry: { RegistryID: 0 }
    }));
  }

  onFormSubmit() {
    const currentUser = this.userService.getCurrentUser();
    
    if (!currentUser?.userId) {
      alert('Please log in first');
      this.router.navigate(['/login']);
      return;
    }
  
    const formValue = this.attendeeForm.value;
    const registrationData = {
      name: formValue.name,
      lastname: formValue.lastname,
      email: formValue.email,
      school: formValue.school || null,
      user: {
        id: currentUser.userId
      }
    };
  
    console.log('Prepared Data:', registrationData);
  
    this.registrationService.createRegistry(registrationData).subscribe({
      next: (response) => {
        console.log('Success:', response);
        const seatsToSave = this.selectedSeats.map(seat => ({
          placement: seat.placement,
          isBooked: true,
          event: { event_id: this.EventID },
          registry: { id: response.id }  // Corrected property name and syntax
        }));
    
        const requests = seatsToSave.map(seat =>
          this.seatService.addSeatForEvent(seat as any, this.EventID).toPromise()
        );Promise.all(requests)
        .then(() => {
          this.bookedSeats.push(...this.selectedSeats);
          this.availableSeats = this.availableSeats.filter(s => !this.selectedSeats.includes(s));
          this.selectedSeats = [];
          alert('Seats booked successfully!');
        })
        .catch(err => {
          console.error('Error saving seats:', err);
          alert(`Error: ${err.error?.message || 'Failed to book seats. Please try again.'}`);
        });
       
      },
      error: (err) => {
        console.error('Full error:', err);
        alert(`Error: ${err.error?.message || 'Registration failed'}`);
      }
    });
  }

  clearSelection() {
    this.selectedSeats = [];
    this.attendeeForm.reset();
  }

  loadEventDetails(): void {
    this.eventService.getEventDetails(this.EventID).subscribe({
      next: (event: Event) => {
        this.event = event;
        this.eventTitle = event.title;
        this.eventLocation = event.location;
        this.startTime = this.formatDateTime(event.startTime);
        this.endTime = this.formatDateTime(event.endTime);
        this.maxAttendees = event.maxAttendees;
        this.loadSeats();
      },
      error: (error) => {
        console.error('Error fetching event details:', error);
        this.eventTitle = 'Error loading event';
        this.eventLocation = 'N/A';
        this.startTime = 'N/A';
        this.endTime = 'N/A';
      }
    });
  }

  private formatDateTime(dateTimeString: string): string {
    try {
      const date = new Date(dateTimeString);
      return date.toLocaleDateString('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      });
    } catch (e) {
      console.error('Error formatting date:', e);
      return dateTimeString;
    }
  }

  selectSeat(seat: Seat): void {
    if (!this.selectedSeats.includes(seat)) {
      this.selectedSeats.push(seat);
    }
  }

  deselectSeat(seat: Seat): void {
    const index = this.selectedSeats.indexOf(seat);
    if (index !== -1) {
      this.selectedSeats.splice(index, 1);
    }
  }

  calculateTotalPrice(): number {
    return this.selectedSeats.length * this.pricePerSeat;
  }

  toggleSeatSelection(seat: Seat): void {
    if (this.isSeatBooked(seat)) {
      alert('This seat is already booked.');
      return;
    }

    if (this.selectedSeats.includes(seat)) {
      this.deselectSeat(seat);
    } else {
      this.selectSeat(seat);
    }
  }

  isSeatSelected(seat: Seat): boolean {
    return this.selectedSeats.includes(seat);
  }

  isSeatBooked(seat: Seat): boolean {
    return this.bookedSeats.some(bookedSeat => bookedSeat.placement === seat.placement);
  }
}