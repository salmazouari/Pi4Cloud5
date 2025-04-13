import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { User } from '../models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  currentUser: User | null = null;

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Check if user is logged in on component initialization
    this.isLoggedIn = this.userService.isLoggedIn();
    
    // Get current user if logged in
    if (this.isLoggedIn) {
      this.currentUser = this.userService.getCurrentUser();
    }

    // Subscribe to user changes for dynamic updates
    this.userService.currentUser$.subscribe(user => {
      this.currentUser = user;
      this.isLoggedIn = !!user;
    });
  }
  navigateToEvents() {
    this.router.navigate(['/events']);
  }

  // Logout function
  logout(): void {
    this.userService.logout().subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Logout failed:', error);
        // Still clear local data and redirect even if API call fails
        localStorage.removeItem('token');
        localStorage.removeItem('currentUser');
        this.router.navigate(['/login']);
      }
    });
  }
}