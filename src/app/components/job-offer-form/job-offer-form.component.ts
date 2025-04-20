import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JobOffer } from 'src/app/models/offer.model';
import { OfferJobService } from 'src/app/services/offer-job.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-job-offer-form',
  templateUrl: './job-offer-form.component.html',
  styleUrls: ['./job-offer-form.component.css']
})
export class JobOfferFormComponent implements OnInit {
  jobOfferForm: FormGroup;
  isEditMode = false;
  offerId: number | null = null;
  loading = false;
  error: string | null = null;
  
  constructor(
    private fb: FormBuilder,
    private offerJobService: OfferJobService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.jobOfferForm = this.fb.group({
      title: ['', [Validators.required]],
      description: ['', [Validators.required]],
      location: ['', [Validators.required]],
      region: ['', [Validators.required]],
      type: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      image: ['']
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.offerId = Number(id);
        this.loadJobOfferDetails(this.offerId);
      }
    });
  }

  loadJobOfferDetails(id: number): void {
    this.loading = true;
    
    this.offerJobService.getJobOfferById(id)
      .subscribe({
        next: (offer) => {
          this.jobOfferForm.patchValue({
            title: offer.title,
            description: offer.description,
            location: offer.location,
            region: offer.region,
            type: offer.type,
            email: offer.email,
            image: offer.image
          });
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to load job offer details';
          this.loading = false;
          console.error(err);
        }
      });
  }

  onSubmit(): void {
    if (this.jobOfferForm.invalid) {
      // Mark all fields as touched to trigger validation messages
      Object.keys(this.jobOfferForm.controls).forEach(field => {
        const control = this.jobOfferForm.get(field);
        control?.markAsTouched({ onlySelf: true });
      });
      return;
    }

    this.loading = true;
    
    // Create job offer with form values
    const jobOfferData: JobOffer = {
      ...this.jobOfferForm.value,
      // Set static recruiter with ID = 2
      recruiter: {
        userId: 1 // Static user ID
      } as User
    };

    console.log('Submitting job offer:', jobOfferData);

    if (this.isEditMode && this.offerId) {
      this.offerJobService.updateJobOffer(this.offerId, jobOfferData)
        .subscribe({
          next: (response) => {
            this.loading = false;
            console.log('Job offer updated successfully:', response);
            // Redirect to job-offers after update
            this.router.navigate(['/job-offers']);
          },
          error: (err) => {
            this.error = 'Failed to update job offer';
            this.loading = false;
            console.error(err);
          }
        });
    } else {
      this.offerJobService.createJobOffer(jobOfferData)
        .subscribe({
          next: (response) => {
            this.loading = false;
            console.log('Job offer created successfully:', response);
            // Redirect to job-offers after create
            this.router.navigate(['/job-offers']);
          },
          error: (err) => {
            this.error = 'Failed to create job offer';
            this.loading = false;
            console.error(err);
          }
        });
    }
  }

  cancel(): void {
    this.router.navigate(['/job-offers']);
  }
}