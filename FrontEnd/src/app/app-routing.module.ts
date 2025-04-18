import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BodyComponent } from './body/body.component';
import { LoginComponent } from './auth/login/login.component';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthGuard } from './auth/auth.guard';
import { EventsComponent } from './events/events.component';
import { EventCardComponent } from './event-card/event-card.component';
import { JoinEventComponent } from './join-event/join-event.component';
import { ReservationComponent } from './reservation/reservation.component';
const routes: Routes = [
  {path: '', component:BodyComponent},
  {path: 'login', component: LoginComponent},
  { path: 'acceuil', component: AcceuilComponent},
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }, 
  { path: 'events', component: EventsComponent },
  { path: 'event-card', component: EventCardComponent },
  { path: 'join', component: JoinEventComponent },
  { path: 'reservation/:id', component: ReservationComponent },// Protect the dashboard route


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
