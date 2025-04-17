import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { UserService } from '../services/user.service'; // Import your UserService

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  sidebarCollapsed = false;
  isDashboardHome = true;

  constructor(
    private router: Router,
    private userService: UserService // Inject UserService
  ) {
    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd)
      )
      .subscribe(event => {
        this.isDashboardHome = event.url === '/dashboard';
      });
  }

  toggleSidebar(): void {
    this.sidebarCollapsed = !this.sidebarCollapsed;
  }

  // Add logout method
  logout(): void {
    this.userService.logout().subscribe({
      next: () => this.handleLogout(),
      error: (error) => {
        console.error('Logout failed:', error);
        this.handleLogout();
      }
    });
  }

  private handleLogout(): void {
    // Clear local storage
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
    // Redirect to login
    this.router.navigate(['/login']);
  }
}