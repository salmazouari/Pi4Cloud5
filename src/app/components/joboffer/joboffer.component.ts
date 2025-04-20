import { Component, OnInit } from '@angular/core';
import { JobOffer } from '../../models/offer.model';
import { OfferJobService } from '../../services/offer-job.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-joboffer',
  templateUrl: './joboffer.component.html',
  styleUrls: ['./joboffer.component.css']
})
export class JobofferComponent implements OnInit {
  jobOffers: JobOffer[] = [];
  loading = false;
  error: string | null = null;

  constructor(
    private offerJobService: OfferJobService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadJobOffers();
  }

  loadJobOffers(): void {
    this.loading = true;
    this.error = null;
    
    this.offerJobService.getAllJobOffers()
      .subscribe({
        next: (offers) => {
          this.jobOffers = offers;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to load job offers';
          this.loading = false;
          console.error(err);
        }
      });
  }

  viewDetails(id: number | undefined): void {
    if (id !== undefined) {
      this.router.navigate(['/job-offers', id]);
    } else {
      console.error('Cannot view details: Job offer ID is undefined');
    }
  }

  createOffer(): void {
    this.router.navigate(['/job-offers/create']);
  }

  editOffer(id: number | undefined): void {
    if (id !== undefined) {
      this.router.navigate(['/job-offers/edit', id]);
    } else {
      console.error('Cannot edit: Job offer ID is undefined');
    }
  }

  deleteOffer(id: number | undefined): void {
    if (id === undefined) {
      console.error('Cannot delete: Job offer ID is undefined');
      return;
    }

    if (confirm('Are you sure you want to delete this job offer?')) {
      this.offerJobService.deleteJobOffer(id).subscribe({
        next: () => {
          this.loadJobOffers();
        },
        error: (err) => {
          console.error('Failed to delete job offer', err);
          alert('Failed to delete job offer');
        }
      });
    }
  }
}