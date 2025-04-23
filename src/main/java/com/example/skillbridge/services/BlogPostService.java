package com.example.skillbridge.services;

import com.example.skillbridge.models.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.awt.print.Pageable;
import java.util.List;

public interface BlogPostService {
    BlogPost createBlogPost(BlogPost blogPost);
    BlogPost getBlogPostById(Long postId);
    List<BlogPost> getAllBlogPosts();
    BlogPost updateBlogPost(Long postId, BlogPost blogPost, Authentication authentication);
    void deleteBlogPost(Long postId, Authentication authentication);
    List<BlogPost> getActiveBlogPosts();
    List<BlogPost> searchBlogPosts(String keyword);
    List<Object[]> getPostCountByCategory();
}