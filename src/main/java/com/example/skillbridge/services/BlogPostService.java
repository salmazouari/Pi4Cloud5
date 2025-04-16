package com.example.skillbridge.services;

import com.example.skillbridge.models.BlogPost;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface BlogPostService {
    BlogPost createBlogPost(BlogPost blogPost);
    BlogPost getBlogPostById(Long postId);
    List<BlogPost> getAllBlogPosts();
    BlogPost updateBlogPost(Long postId, BlogPost blogPost);
    void deleteBlogPost(Long postId);
    List<BlogPost> getActiveBlogPosts(); // Exclude soft-deleted posts
}