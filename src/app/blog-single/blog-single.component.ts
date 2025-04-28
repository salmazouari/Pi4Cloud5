import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BlogService } from '../services/blog.service';
import { CommentService } from '../services/comment.service'; // ✅ Import the CommentService
import { BlogPost } from '../models/blog-post';
import { Comment } from '../models/comment'; // ✅ Import the Comment model

@Component({
  selector: 'app-blog-single',
  templateUrl: './blog-single.component.html',
  styleUrls: ['./blog-single.component.css']
})
export class BlogSingleComponent implements OnInit {
  postId!: number;
  post!: BlogPost;
  comments: Comment[] = []; // Store the comments related to the post
  isLoading: boolean = true;
  error: string = '';

  constructor(
    private route: ActivatedRoute,
    private blogService: BlogService,
    private commentService: CommentService // ✅ Inject CommentService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.postId = Number(id);

        // Fetch the blog post details
        this.blogService.getPost(this.postId).subscribe(
          (data: BlogPost) => {
            this.post = data;

            // Fetch the comments related to the post
            this.loadComments();
          },
          (error) => {
            this.error = 'An error occurred while fetching the blog post.';
            console.error(error);
            this.isLoading = false;
          }
        );
      }
    });
  }

  loadComments() {
    this.commentService.getCommentsByPost(this.postId).subscribe({
      next: (comments) => {
        this.comments = comments; // Store the comments
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'An error occurred while fetching the comments.';
        console.error(err);
        this.isLoading = false;
      }
    });
  }
}
