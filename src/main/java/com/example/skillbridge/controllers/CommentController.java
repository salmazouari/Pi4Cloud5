package com.example.skillbridge.controllers;

import com.example.skillbridge.exceptions.ResourceNotFoundException;
import com.example.skillbridge.models.Comment;
import com.example.skillbridge.models.BlogPost;
import com.example.skillbridge.models.User;
import com.example.skillbridge.services.CommentService;
import com.example.skillbridge.services.BlogPostService;
import com.example.skillbridge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

   @Autowired
   CommentService commentService;
    @Autowired
    BlogPostService blogPostService;  // Service to fetch BlogPost
    @Autowired
    UserService userService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
        this.blogPostService = blogPostService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            BlogPost post = blogPostService.getBlogPostById(comment.getPost().getId());
            User user = userService.getUserById(comment.getUser().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            comment.setPost(post);
            comment.setUser(user);

            Comment createdComment = commentService.createComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);

        } catch (IllegalArgumentException e) {
            // This handles the bad words exception
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Bad Request", "message", e.getMessage())
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Not Found", "message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Internal Server Error", "message", e.getMessage())
            );
        }
    }

    @GetMapping("/{commentId}")
    public Comment getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> getCommentsByUser(@PathVariable Long userId) {
        return commentService.getCommentsByUser(userId);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId, @RequestBody Comment comment) {
        return commentService.updateComment(commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}