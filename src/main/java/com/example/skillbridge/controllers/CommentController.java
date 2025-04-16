package com.example.skillbridge.controllers;

import com.example.skillbridge.models.Comment;
import com.example.skillbridge.models.BlogPost;
import com.example.skillbridge.models.User;
import com.example.skillbridge.services.CommentService;
import com.example.skillbridge.services.BlogPostService;
import com.example.skillbridge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) {
        BlogPost post = blogPostService.getBlogPostById(comment.getPost().getId());
        User user = userService.getUserById(comment.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setPost(post);
        comment.setUser(user);
        return commentService.createComment(comment);
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