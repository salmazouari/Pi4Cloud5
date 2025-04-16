import { Component, Input, OnInit } from '@angular/core';
import { Comment } from '../models/comment';
import { CommentService } from '../services/comment.service';
import { UserService } from '../services/user.service'; // Import the UserService
import { User } from '../models/user.model'; // Import User model

@Component({
  selector: 'app-comments-section',
  templateUrl: './comments-section.component.html',
  styleUrls: ['./comments-section.component.css']
})
export class CommentsSectionComponent implements OnInit {
  @Input() comments: Comment[] = [];
  @Input() postId!: number; // Receive postId from parent component

  newCommentContent: string = '';

  // Variables for editing a comment
  editingCommentId: number | null = null;
  editedCommentContent: string = '';

  currentUser: User | null = null; // Variable to store the current logged-in user

  constructor(
    private commentService: CommentService,
    private userService: UserService // Inject the UserService
  ) {}

  ngOnInit(): void {
    // Retrieve the logged-in user
    this.currentUser = this.userService.getCurrentUser();
  }

  submitComment() {
    if (!this.newCommentContent.trim()) {
      return; // Don't submit empty comments
    }

    if (!this.currentUser) {
      alert("You must be logged in to post a comment.");
      return;
    }

    const newComment: Comment = {
      content: this.newCommentContent,
      post: { id: this.postId },
      user: {
        userId: this.currentUser.userId,
        username: this.currentUser.username || 'Anonymous', // Fallback if username is missing
        imageUrl: this.currentUser.imageUrl || 'assets/images/default-avatar.png' // Default image if not provided
      } // Use the current user's ID
    };

    this.commentService.createComment(newComment).subscribe({
      next: (createdComment) => {
        this.comments.push(createdComment); // Update the list immediately
        this.newCommentContent = ''; // Reset the form
      },
      error: (err) => {
        console.error('Error creating comment:', err);
      }
    });
  }

  // Start editing a comment
  startEditing(comment: Comment) {
    this.editingCommentId = comment.id ?? null;
    this.editedCommentContent = comment.content;
  }

  // Cancel editing
  cancelEditing() {
    this.editingCommentId = null;
    this.editedCommentContent = '';
  }

  // Save the edited comment
  saveComment(comment: Comment) {
    if (this.editedCommentContent.trim()) {
      comment.content = this.editedCommentContent;

      this.commentService.updateComment(comment.id!, comment).subscribe({
        next: (updatedComment) => {
          const index = this.comments.findIndex(c => c.id === updatedComment.id);
          if (index !== -1) {
            this.comments[index] = updatedComment;
          }
          this.cancelEditing();
        },
        error: (err) => {
          console.error('Error updating comment:', err);
        }
      });
    }
  }

  // Delete a comment
  deleteComment(commentId: number) {
    if (confirm('Are you sure you want to delete this comment?')) {
      this.commentService.deleteComment(commentId).subscribe({
        next: () => {
          this.comments = this.comments.filter(comment => comment.id !== commentId);
        },
        error: (err) => {
          console.error('Error deleting comment:', err);
        }
      });
    }
  }
}
