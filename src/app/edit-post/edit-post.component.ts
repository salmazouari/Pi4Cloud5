import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BlogService } from '../services/blog.service';
import { BlogPost } from '../models/blog-post';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {
  postId!: number;
  postForm!: FormGroup;
  loading = false;
  successMessage = '';
  categories: any[] = []; // To hold categories

  constructor(
    private route: ActivatedRoute,
    private blogService: BlogService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.postId = Number(this.route.snapshot.paramMap.get('id'));

    // Initialize the form with disabled 'author' field
    this.postForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
      author: [{ value: '', disabled: true }], // Disabled author field (read-only)
      category: ['', Validators.required] // Category is required
    });

    this.loadPost();
    this.loadCategories(); // Fetch categories when the component initializes
  }

  // Load post data into the form
  loadPost(): void {
    this.loading = true;
    this.blogService.getPost(this.postId).subscribe({
      next: (post: BlogPost) => {
        // Pre-populate the form with the post data
        if (post.author) {
          this.postForm.patchValue({
            title: post.title,
            content: post.content,
            author: post.author.username, // Display author's name
            category: post.category?.id || '' // Set category ID in the form (handle undefined)
          });
        } else {
          // If no author, set a default value or handle accordingly
          this.postForm.patchValue({
            title: post.title,
            content: post.content,
            author: '', // Or you can leave this empty, since it's disabled
            category: post.category?.id || '' // Handle undefined category
          });
        }
        this.loading = false;
      },
      error: () => {
        alert('Error loading post');
        this.loading = false;
      }
    });
  }

  // Load categories from the backend
  loadCategories(): void {
    this.blogService.getCategories().subscribe({
      next: (data) => {
        this.categories = data; // Populate categories array
      },
      error: (err) => {
        console.error('Error loading categories:', err);
      }
    });
  }

  // Handle form submission
  onSubmit(): void {
    if (this.postForm.invalid) {
      this.postForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    // Clone the form data and ensure category is an object with an id
    const postData = { ...this.postForm.value };

    // Ensure the 'category' is sent as an object with an id (convert to number if needed)
    const categoryId = +postData.category; // Convert category to a number
    if (!categoryId) {
      alert('Please select a valid category');
      this.loading = false;
      return;
    }
    
    postData.category = { id: categoryId };  // Make category an object with an id

    // Exclude the 'author' field since it's not meant to be updated
    delete postData.author;

    // Log the post data to verify it's being sent correctly
    console.log('Post data to send:', postData);

    // Send the data to the backend to update the post
    this.blogService.updatePost(this.postId, postData).subscribe({
      next: () => {
        this.loading = false;
        this.successMessage = 'Post updated successfully!';
        setTimeout(() => this.router.navigate(['/blog']), 1500);
      },
      error: (err) => {
        console.error('Error updating post:', err);
        alert('Error updating post');
        this.loading = false;
      }
    });
  }

  // Handle cancel button to navigate back to the blog list
  onCancel(): void {
    this.router.navigate(['/blog']);
  }
}
