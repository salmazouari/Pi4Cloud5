import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JobOffer } from 'src/app/models/offer.model';
import { OfferJobService } from 'src/app/services/offer-job.service';

@Component({
  selector: 'app-job-application-details',
  templateUrl: './job-application-details.component.html',
  styleUrls: ['./job-application-details.component.css']
})
export class JobApplicationDetailsComponent implements OnInit {
  jobOffer: JobOffer | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private offerJobService: OfferJobService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) {
        this.loadJobOfferDetails(id);
      }
    });
  }

  loadJobOfferDetails(id: number): void {
    this.loading = true;
    this.error = null;
    
    this.offerJobService.getJobOfferById(id)
      .subscribe({
        next: (offer) => {
          this.jobOffer = offer;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to load job offer details';
          this.loading = false;
          console.error(err);
        }
      });
  }

 

  goBack(): void {
    this.router.navigate(['/job-app']);
  }

  deleteOffer(): void {
    if (!this.jobOffer || !this.jobOffer.id) return;

    if (confirm('Are you sure you want to delete this job offer?')) {
      this.offerJobService.deleteJobOffer(this.jobOffer.id).subscribe({
        next: () => {
          this.router.navigate(['/job-app']);
        },
        error: (err) => {
          console.error('Failed to delete job offer', err);
          alert('Failed to delete job offer');
        }
      });
    }
  }
}
