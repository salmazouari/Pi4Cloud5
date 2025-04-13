import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Seat } from 'src/app/models/seats.model';

@Injectable({
  providedIn: 'root',
})
export class SeatService {
  private apiUrl = 'http://localhost:8093/seats'; // Update with your backend URL

  constructor(private http: HttpClient) {}
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // Get all seats for a specific event
  getSeatsByEventId(eventId: number): Observable<Seat[]> {
    return this.http.get<Seat[]>(`${this.apiUrl}/events/${eventId}/seats`, { headers: this.getAuthHeaders() });
  }

  // Add a seat to an event
  addSeatForEvent(seat: Seat, eventId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/add/${eventId}`, seat, { headers: this.getAuthHeaders() });
  }

  // Get a single seat by ID
  getSeatById(id: number): Observable<Seat> {
    return this.http.get<Seat>(`${this.apiUrl}/${id}`);
  }

  // Update a seat
  updateSeat(seat: Seat): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${seat.id}`, seat);
  }

  // Delete a seat
  deleteSeat(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Get event details (if needed for other components)
  getEventDetails(eventId: number): Observable<Event> {
    return this.http.get<Event>(`/api/events/${eventId}`); // Adjust URL as needed
  }
}