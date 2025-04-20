import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BlogService } from '../services/blog.service';
import { UserService } from '../services/user.service'; // ✅ Import UserService

@Component({
  selector: 'app-post-blog',
  templateUrl: './post-blog.component.html',
  styleUrls: ['./post-blog.component.css']
})
export class PostBlogComponent implements OnInit {
  blogForm!: FormGroup;
  categories: any[] = [];
  isGenerating = false;
  generationError: string | null = null;


  constructor(
    private fb: FormBuilder,
    private blogService: BlogService,
    private router: Router,
    private userService: UserService // ✅ Inject UserService
  ) {}

  ngOnInit() {
    const currentUser = this.userService.getCurrentUser();

    if (!currentUser) {
      console.error('No user is logged in');
      this.router.navigate(['/login']); // Optional: Redirect to login if no user
      return;
    }

    this.blogForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
      slug: ['', Validators.required],
      author: this.fb.group({
        userId: [currentUser.userId, Validators.required] // ✅ Set current user ID
      }),
      category: this.fb.group({
        id: [null, Validators.required]
      })
    });

    this.blogService.getCategories().subscribe(
      data => {
        this.categories = data;
      },
      error => {
        console.error('Error fetching categories', error);
      }
    );
  }

  onSubmit() {
    console.log('onSubmit called'); // Debug: Check if the method is called
    if (this.blogForm.valid) {
      console.log('Form is valid, submitting:', this.blogForm.value); // Debug: Log the form data
      const post = this.blogForm.value;
      this.blogService.createPost(post).subscribe(
        response => {
          console.log('Post created successfully', response);
          console.log('Navigating to /blog'); // Debug: Confirm navigation
          this.router.navigate(['/blog']);
        },
        error => {
          console.error('Error creating post:', error); // Ensure the error is logged
        }
      );
    } else {
      console.log('Form is invalid:', this.blogForm.errors); // Debug: Log form errors
      console.log('Form value:', this.blogForm.value); // Debug: Log form values
      console.log('Form controls:', this.blogForm.controls); // Debug: Log control states
    }
  }

  preview() {
    console.log('Preview:', this.blogForm.value);
  }

  generateAIContent() {
    const idea = this.blogForm.get('title')?.value;
    
    if (!idea) {
      this.generationError = 'Please enter a title first';
      return;
    }

    this.isGenerating = true;
    this.generationError = null;

    this.blogService.generateAIContent(idea).subscribe({
      next: (response) => {
        this.blogForm.patchValue({
          content: response.content
        });
        this.isGenerating = false;
      },
      error: (err) => {
        console.error('Generation error:', err);
        this.generationError = 'Failed to generate content. Please try again.';
        this.isGenerating = false;
      }
    });
  }
  
}
