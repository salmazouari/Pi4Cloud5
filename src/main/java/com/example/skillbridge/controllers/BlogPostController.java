package com.example.skillbridge.controllers;

import com.example.skillbridge.models.BlogPost;
import com.example.skillbridge.services.BlogPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/blog-posts")
@CrossOrigin(origins = "http://localhost:4200")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
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
    public BlogPost updateBlogPost(@PathVariable Long id, @RequestBody BlogPost blogPost) {
        return blogPostService.updateBlogPost(id, blogPost);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteBlogPost(id);
    }

    @GetMapping
    public Iterable<BlogPost> getAllBlogPosts() {
        return blogPostService.getAllBlogPosts();
    }


}