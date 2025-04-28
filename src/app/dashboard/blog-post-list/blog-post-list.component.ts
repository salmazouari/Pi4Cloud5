import { Component, OnInit } from '@angular/core';
import { BlogService } from '../../services/blog.service';
import { CommentService } from '../../services/comment.service';


@Component({
  selector: 'app-blog-post-list',
  templateUrl: './blog-post-list.component.html',
  styleUrls: ['./blog-post-list.component.css']
})
export class BlogPostListComponent implements OnInit {
  postsWithComments: any[] = [];
  isLoading = true;

  constructor(private blogService: BlogService, private commentService: CommentService ) { }

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.blogService.getAllPostsWithComments().subscribe({
      next: (data) => {
        this.postsWithComments = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading posts:', error);
        this.isLoading = false;
      }
    });
  }

  deletePost(postId: number): void {
    if (confirm('Are you sure you want to delete this post?')) {
      this.blogService.deletePost(postId).subscribe({
        next: () => {
          this.postsWithComments = this.postsWithComments.filter(
            post => post.post.id !== postId
          );
        },
        error: (error) => console.error('Error deleting post:', error)
      });
    }
  }

  deleteComment(commentId: number): void {
    if (confirm('Are you sure you want to delete this comment?')) {
      this.commentService.deleteComment(commentId).subscribe({
        next: () => {
          // Remove comment from UI
          this.postsWithComments = this.postsWithComments.map(post => {
            return {
              ...post,
              comments: post.comments.filter((comment: any) => comment.id !== commentId)
            };
          });
        },
        error: (err) => console.error('Error deleting comment:', err)
      });
    }
  }


}
