// src/app/services/registration.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Registration } from '../models/registry.model';
import { UserService } from '../user.service';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private apiUrl = 'http://localhost:8093/Registry';

  constructor(private http: HttpClient,private userService:UserService) {}
    private getAuthHeaders(): HttpHeaders {
      const token = localStorage.getItem('token');
      return new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      });
    }
    createRegistry(registryData: any): Observable<Registration> {
      // Ensure payload matches backend exactly
      const payload = {
        name: registryData.name,
        lastname: registryData.lastname,
        email: registryData.email,
        school: registryData.school || null,
        user: {
          userId: registryData.user.id // Change this to match your Java entity
        }
      };
    
      console.log('Final API Payload:', payload); // Verify before sending
      
      return this.http.post<Registration>(
        `${this.apiUrl}/registries`,
        payload,
        { 
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
           
          })
        }
      );
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