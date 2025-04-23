package com.example.skillbridge.controllers;

import com.example.skillbridge.models.BlogPost;
import com.example.skillbridge.services.BlogPostService;
import com.example.skillbridge.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog-posts")
@CrossOrigin(origins = "http://localhost:4200")
public class BlogPostController {

    private final BlogPostService blogPostService;
    private final CommentService commentService;


    public BlogPostController(BlogPostService blogPostService, CommentService commentService) {
        this.blogPostService = blogPostService;
        this.commentService = commentService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // ✅ Use AUTHORITIES, not roles
    public BlogPost createBlogPost(@RequestBody BlogPost blogPost) {
        return blogPostService.createBlogPost(blogPost);
    }

    @GetMapping("/{id}")
    public BlogPost getBlogPost(@PathVariable Long id) {
        return blogPostService.getBlogPostById(id);
    }

    @PutMapping("/{id}")
    public BlogPost updateBlogPost(@PathVariable Long id, @RequestBody BlogPost blogPost, Authentication authentication) {
        return blogPostService.updateBlogPost(id, blogPost, authentication);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogPost(@PathVariable Long id, Authentication authentication) {
        blogPostService.deleteBlogPost(id, authentication);
    }

    @GetMapping
    public Iterable<BlogPost> getAllBlogPosts() {
        return blogPostService.getAllBlogPosts();
    }


    // Add this new admin endpoint
    @GetMapping("/admin/all-with-comments")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getAllPostsWithComments() {
        List<BlogPost> posts = blogPostService.getAllBlogPosts();

        List<Map<String, Object>> response = posts.stream()
                .map(post -> {
                    Map<String, Object> postData = new LinkedHashMap<>();
                    postData.put("post", post);
                    postData.put("comments", commentService.getCommentsByPost(post.getId()));
                    return postData;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/stats/posts-by-category")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getPostCountByCategory() {
        List<Object[]> stats = blogPostService.getPostCountByCategory();

        List<Map<String, Object>> response = stats.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("category", row[0]);
            map.put("count", row[1]);
            return map;
        }).toList();

        return ResponseEntity.ok(response);
    }

}