import { Component, OnInit } from '@angular/core';
import { BlogService } from '../services/blog.service';
import { BlogPost } from '../models/blog-post';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css']
})
export class BlogListComponent implements OnInit {
  posts: BlogPost[] = [];
  errorMessage = '';
  searchControl = new FormControl('');

  constructor(private blogService: BlogService, private router: Router) {}

  ngOnInit(): void {
    this.loadPosts();

    this.searchControl.valueChanges.pipe(
      debounceTime(300), // Wait 300ms after user stops typing
      distinctUntilChanged(), // Only emit if value has changed
      switchMap((query: string | null) => {
        const safeQuery = query?.trim() || ''; // Handle null and whitespace
        if (safeQuery.length === 0) {
          return this.blogService.getPosts();
        }
        return this.blogService.searchPosts(safeQuery);
      })      
    ).subscribe({
      next: posts => this.posts = posts,
      error: err => {
        console.error('Search error:', err);
        this.errorMessage = 'Search failed.';
      }
    });
  }

  loadPosts(): void {
    this.blogService.getPosts().subscribe({
      next: posts => this.posts = posts,
      error: err => {
        console.error('Error fetching posts:', err);
        this.errorMessage = 'Failed to load posts.';
      }
    });
  }

  deletePost(id: number): void {
    if (confirm('Are you sure you want to delete this post?')) {
      this.blogService.deletePost(id).subscribe({
        next: () => {
          this.posts = this.posts.filter(p => p.id !== id);
          alert('Post deleted successfully!');
        },
        error: err => {
          console.error('Delete error:', err);
          alert('Failed to delete the post.');
        }
      });
    }
  }

  editPost(id: number): void {
    this.router.navigate(['/edit', id]);
  }
}
