import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JobApplication } from '../models/jobapplication.model';

// Removed duplicate declaration of JobApplicationService

@Injectable({
  providedIn: 'root'
})
export class JobApplicationOfferService {

  private apiUrl = 'http://localhost:8093/api/job-applications'; // à adapter selon ton backend

  constructor(private http: HttpClient) {}

  applyToJob(formData: FormData, application: JobApplication): Observable<any> {
    let params = new HttpParams()
      .set('studentId', application.studentId)
      .set('jobOfferId', application.jobOfferId);

    if (application.cv) {
      params = params.set('cv', application.cv);
    }

    if (application.motivationLetter) {
      params = params.set('motivationLetter', application.motivationLetter);
    }

    return this.http.post(`${this.apiUrl}/apply`, null, { params });
  }

}
