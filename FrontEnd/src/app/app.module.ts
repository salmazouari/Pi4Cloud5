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
import { EventCardComponent } from './event-card/event-card.component';
import { JoinEventComponent } from './join-event/join-event.component';
import { ReservationComponent } from './reservation/reservation.component';

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
    EventCardComponent,
    JoinEventComponent,
    ReservationComponent
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
