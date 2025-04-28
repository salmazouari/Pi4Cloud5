import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BlogPost } from '../models/blog-post';
import { UserService } from './user.service'; // Add UserService

@Injectable({
  providedIn: 'root'
})
export class BlogService {
  private baseUrl = 'http://localhost:8080/api/blog-posts';
  private aiGenerateUrl = 'http://localhost:8080/api/blog/generate'; // Add this

  constructor(private http: HttpClient, private userService: UserService) { }

  // Helper method for authorization headers
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken'); // Or: this.userService.tokenSubject.value
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // Create a new blog post (POST)
  createPost(post: BlogPost): Observable<BlogPost> {
    return this.http.post<BlogPost>(this.baseUrl, post, { headers: this.getAuthHeaders() });
  }

  // Get all blog posts (GET)
  getPosts(): Observable<BlogPost[]> {
    return this.http.get<BlogPost[]>(this.baseUrl);
  }

  // Get a single blog post by ID (GET)
  getPost(id: number): Observable<BlogPost> {
    return this.http.get<BlogPost>(`${this.baseUrl}/${id}`);
  }

  // Update a blog post (PUT)
  updatePost(id: number, post: Partial<BlogPost>): Observable<BlogPost> {
    return this.http.put<BlogPost>(`${this.baseUrl}/${id}`, post, { headers: this.getAuthHeaders() });
  }

  // Delete a blog post (DELETE)
  deletePost(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  // Get authors (GET)
  getAuthors(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/users');
  }

  // Get categories (GET)
  getCategories(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/categories');
  }

  generateAIContent(idea: string): Observable<{ content: string }> {
    return this.http.post<{ content: string }>(
      this.aiGenerateUrl,
      { idea },
      { headers: this.getAuthHeaders() }
    );
  }

  searchPosts(query: string): Observable<BlogPost[]> {
    return this.http.get<BlogPost[]>(`http://localhost:8080/api/blog/search?q=${encodeURIComponent(query)}`);
  }

  // Add this method to your BlogService
getAllPostsWithComments(): Observable<any[]> {
  return this.http.get<any[]>(
    'http://localhost:8080/api/blog-posts/admin/all-with-comments',
    { headers: this.getAuthHeaders() }
  );
}

}
