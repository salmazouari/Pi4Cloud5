import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from '../../models/events.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private baseUrl = 'http://localhost:8093/events'; // your actual backend URL

  constructor(private http: HttpClient) {}

  // Helper method to get auth headers
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // Create event (requires auth)
  createEvent(eventData: any): Observable<Event> {
    return this.http.post<Event>(this.baseUrl, eventData, { headers: this.getAuthHeaders() });
  }

  // Fetch all events (no auth needed?)
  getAllEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(this.baseUrl);
  }

  // Get event title by ID (auth optional – adapt if needed)
  getEventTitle(eventId: number): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/${eventId}/title`);
  }

  // Get event location by ID
  getEventLocation(eventId: number): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/${eventId}/location`);
  }

  // Delete event (requires auth)
  deleteEvent(eventId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${eventId}`, { headers: this.getAuthHeaders() });
  }

  // Update event (requires auth)
  updateEvent(event: Event): Observable<Event> {
    return this.http.put<Event>(`${this.baseUrl}/${event.eventID}`, event, { headers: this.getAuthHeaders() });
  }

  // Get all event details
  getEventDetails(eventId: number): Observable<Event> {
    return this.http.get<Event>(`${this.baseUrl}/${eventId}`);
  }
}
