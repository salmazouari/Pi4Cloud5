import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobOffer } from '../models/offer.model';

@Injectable({
  providedIn: 'root'
})
export class OfferJobService {
  private apiUrl = 'http://localhost:8093/api/joboffers'; // Adjust if your backend is running on a different URL

  constructor(private http: HttpClient) { }
  // Create a new job offer
  createJobOffer(jobOffer: JobOffer): Observable<JobOffer> {
    return this.http.post<JobOffer>(`${this.apiUrl}/createoffer`, jobOffer);
  }
  

  // Get all job offers
  getAllJobOffers(): Observable<JobOffer[]> {
    return this.http.get<JobOffer[]>(`${this.apiUrl}/alloffers`);
  }

  // Get job offer by ID
  getJobOfferById(id: number): Observable<JobOffer> {
    return this.http.get<JobOffer>(`${this.apiUrl}/${id}`);
  }

  // Update job offer
  updateJobOffer(id: number, jobOffer: JobOffer): Observable<JobOffer> {
    return this.http.put<JobOffer>(`${this.apiUrl}/${id}`, jobOffer);
  }

  // Delete job offer
  deleteJobOffer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}