// src/app/services/registration.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Registration } from '../models/registry.model';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private apiUrl = 'http://localhost:8093/registries';

  constructor(private http: HttpClient) {}

  createRegistry(registration: Registration): Observable<Registration> {
    // Convert seat IDs to backend format
    const requestBody = {
      ...registration,
      seats: registration.seats.map(seat => ({ id: seat.id }))
    };
    return this.http.post<Registration>(this.apiUrl, requestBody);
  }


  getAllRegistries(): Observable<Registration[]> {
    return this.http.get<Registration[]>(this.apiUrl);
  }

  getRegistryById(id: number): Observable<Registration> {
    return this.http.get<Registration>(`${this.apiUrl}/${id}`);
  }

  updateRegistry(id: number, registration: Registration): Observable<Registration> {
    return this.http.put<Registration>(`${this.apiUrl}/${id}`, registration);
  }

  deleteRegistry(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}