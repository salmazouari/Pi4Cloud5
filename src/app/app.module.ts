import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { BodyComponent } from './body/body.component';
import { FooterComponent } from './footer/footer.component';
import { BlogBodyComponent } from './blog-body/blog-body.component';
import { EventsComponent } from './events/events.component';
import { LoginComponent } from './auth/login/login.component';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import { JobofferComponent } from './components/joboffer/joboffer.component';
import { JobOfferFormComponent } from './components/job-offer-form/job-offer-form.component';
import { JobOfferDetailComponent } from './components/job-offer-detail/job-offer-detail.component';
import { JobApplicationComponent } from './components/job-application/job-application.component';
import { JobApplicationDetailsComponent } from './components/job-application-details/job-application-details.component';
import { JobApplicationSubmitComponent } from './components/job-application-submit/job-application-submit.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BodyComponent,
    FooterComponent,
    BlogBodyComponent,
    EventsComponent,
    LoginComponent,
    AcceuilComponent,
    DashboardComponent,
    JobofferComponent,
    
    JobOfferDetailComponent,
    JobOfferFormComponent,
    JobApplicationComponent,
    JobApplicationDetailsComponent,
    JobApplicationSubmitComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
