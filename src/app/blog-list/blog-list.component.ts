// src/app/blog-list/blog-list.component.ts
import { Component, OnInit } from '@angular/core';
import { BlogService } from '../services/blog.service';
import { BlogPost } from '../models/blog-post';
import { Router } from '@angular/router'; // ✅ Import Router


@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css']
})
export class BlogListComponent implements OnInit {
  posts: BlogPost[] = [];
  errorMessage: string = '';

  constructor(private blogService: BlogService, private router: Router) { }

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.blogService.getPosts().subscribe({
      next: (data: BlogPost[]) => this.posts = data,
      error: (err) => {
        console.error('Error fetching blog posts:', err);
        this.errorMessage = 'Error loading posts. Please try again later.';
      }
    });
  }

  deletePost(id: number): void {
    if (confirm('Are you sure you want to delete this post?')) {
      this.blogService.deletePost(id).subscribe({
        next: () => {
          // Remove the post from the list
          this.posts = this.posts.filter(post => post.id !== id);
          console.log('Post deleted successfully');
          alert('Post deleted successfully!');
      },
      error: (err) => {
        console.error('Error deleting post:', err);
        alert('Failed to delete the post. Please try again.');
      }
    });
  }
}

editPost(id: number): void {
  this.router.navigate(['/edit', id]);
}

}
