import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JobOffer } from 'src/app/models/offer.model';
import { JobApplication } from 'src/app/models/jobapplication.model';
import { OfferJobService } from 'src/app/services/offer-job.service';
import { JobApplicationOfferService } from 'src/app/services/job-application.service';

@Component({
  selector: 'app-job-application',
  templateUrl: './job-application.component.html',
  styleUrls: ['./job-application.component.css']
})
export class JobApplicationComponent implements OnInit {
  jobOffers: JobOffer[] = [];
  loading = false;
  error: string | null = null;

  // Modifier le type pour accepter number | undefined | null
  selectedOfferId: number | null = null;
  userId = 1; // ID utilisateur statique pour les tests
  cv: File | null = null;
  motivationLetter: File | null = null;

  constructor(
    private offerJobService: OfferJobService,
    private jobService: JobApplicationOfferService,
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
      this.router.navigate(['/job-details', id]);
    } else {
      console.error('Cannot view details: Job offer ID is undefined');
    }
  }

  // Ouvrir le formulaire d'application pour une offre sélectionnée
  openApplicationForm(offerId: number | undefined): void {
    if (offerId === undefined) {
      console.error('Cannot open application form: Job offer ID is undefined');
      return;
    }

    this.selectedOfferId = offerId;
  }

  // Annuler l'application
  cancelApplication(): void {
    this.selectedOfferId = null;
    this.cv = null;
    this.motivationLetter = null;
  }

  // Gérer le changement de fichier
  onFileChange(event: any, type: string): void {
    const file = event.target.files[0];
    if (type === 'cv') {
      this.cv = file;
    } else if (type === 'motivationLetter') {
      this.motivationLetter = file;
    }
  }

  // Soumettre l'application
  submitApplication(): void {
    if (this.selectedOfferId === null) {
      console.error('Cannot submit application: No job offer selected');
      return;
    }

    if (!this.cv || !this.motivationLetter) {
      alert('Please upload both the CV and the Motivation Letter!');
      return;
    }

    const application: JobApplication = {
      studentId: this.userId,
      jobOfferId: this.selectedOfferId,
      status: 'PENDING',
      applicationDate: new Date().toISOString().split('T')[0], // format yyyy-MM-dd
      cv: this.cv.name, // Le nom du fichier pour le CV
      motivationLetter: this.motivationLetter.name // Le nom du fichier pour la lettre de motivation
    };

    const formData = new FormData();
    formData.append('cv', this.cv, this.cv.name);
    formData.append('motivationLetter', this.motivationLetter, this.motivationLetter.name);

    // Vous pouvez ici envoyer formData à votre API backend avec un service
    this.jobService.applyToJob(formData, application).subscribe({
      next: res => {
        alert("Application submitted successfully!");
        this.cancelApplication(); // Réinitialiser le formulaire après la soumission
      },
      error: err => {
        console.error("Submission error:", err);
        alert("Error submitting application: " + (err.error?.message || err.message || 'Unknown error'));
      }
    });
  }
}
