import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BodyComponent } from './body/body.component';
import { LoginComponent } from './auth/login/login.component';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthGuard } from './auth/auth.guard';
import { JobofferComponent } from './components/joboffer/joboffer.component';
import { JobOfferDetailComponent } from './components/job-offer-detail/job-offer-detail.component';
import { JobOfferFormComponent } from './components/job-offer-form/job-offer-form.component';
import { JobApplicationComponent } from './components/job-application/job-application.component';
import { JobApplicationDetailsComponent } from './components/job-application-details/job-application-details.component';

const routes: Routes = [
  { path: '', component: BodyComponent },
  { path: 'login', component: LoginComponent },
  { path: 'acceuil', component: AcceuilComponent },
  { path: 'job-offers', component: JobofferComponent },
  { path: 'job-offers/create', component: JobOfferFormComponent },
  { path: 'job-offers/:id', component: JobOfferDetailComponent },
  { path: 'job-offers/edit/:id', component: JobOfferFormComponent },
  { path: 'job-app', component: JobApplicationComponent },
  { path: 'job-details/:id', component: JobApplicationDetailsComponent },


  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }, // Protect the dashboard route


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
