import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BodyComponent } from './body/body.component';
import { LoginComponent } from './auth/login/login.component';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  {path: '', component:BodyComponent},
  {path: 'login', component: LoginComponent},
  { path: 'acceuil', component: AcceuilComponent},
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }, // Protect the dashboard route


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
