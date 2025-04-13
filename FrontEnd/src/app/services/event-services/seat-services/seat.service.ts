import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from '../../../models/events.model';  // Import Event model
import { Seat } from '../../../models/seats.model';   // Import Seat model

@Injectable({
  providedIn: 'root',
})
export class SeatService {
  private apiUrl = 'http://localhost:8093/seats';  // Update with your backend URL

  constructor(private http: HttpClient) {}

  // Method to create or update a seat
  createOrUpdateSeat(seat: Seat): Observable<any> {
    return this.http.post<any>(this.apiUrl, seat);
  }

  // Method to delete a seat
  deleteSeat(placement: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${placement}`);
  }

  // Method to get all seats (optional)
  getAllSeats(): Observable<Seat[]> {
    return this.http.get<Seat[]>(this.apiUrl);
  }

  getEventDetails(eventID: string): Observable<Event> {
    return this.http.get<Event>(`/api/events/${eventID}`);
  }

  getSeatsForEvent(eventId: string | number): Observable<Seat[]> {
    return this.http.get<Seat[]>(`${this.apiUrl}/event/${eventId}`);
  }
 // seat.service.ts
// seat.service.ts
addSeatForEvent(seat: { placement: string, isBooked: boolean, event: { EventID: number } }, eventID: number): Observable<any> {
  return this.http.post(`${this.apiUrl}/add/${eventID}`, seat);
}
 updateSeat(seat: Seat): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/update/${seat.id}`, seat);
  }
  getEventTitle(id: number): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/${id}/title`);
  }

  // Fetch event location
  getEventLocation(id: number): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/${id}/location`);
  }
  getAvailableSeats(eventId: number): Observable<Seat[]> {
    return this.http.get<Seat[]>(`${this.apiUrl}/events/${eventId}/seats`);
  }
}