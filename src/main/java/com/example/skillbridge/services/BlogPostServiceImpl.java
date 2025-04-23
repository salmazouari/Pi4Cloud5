package com.example.skillbridge.services;

import com.example.skillbridge.exceptions.ResourceNotFoundException;
import com.example.skillbridge.models.BlogPost;
import com.example.skillbridge.models.Category;
import com.example.skillbridge.models.User;
import com.example.skillbridge.repositories.BlogPostRepository;
import com.example.skillbridge.repositories.CategoryRepository;
import com.example.skillbridge.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;  // Correct import

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService; // Add this dependency
    private final EmailService emailService; // Add this

    @Override
    public BlogPost createBlogPost(BlogPost blogPost) {
        // Validate author exists
        User author = userRepository.findById(blogPost.getAuthor().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + blogPost.getAuthor().getUserId()));
        System.out.println("authorId reçu : " + blogPost.getAuthor().getUserId());

        // Validate category exists
        Category category = categoryRepository.findById(blogPost.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + blogPost.getCategory().getId()));
        System.out.println("categoryId reçu : " + blogPost.getCategory().getId());
        // Validate unique slug
        if (blogPost.getSlug() != null && blogPostRepository.existsBySlug(blogPost.getSlug())) {
            throw new IllegalArgumentException("Slug '" + blogPost.getSlug() + "' already exists");
        }

        blogPost.setAuthor(author);
        blogPost.setCategory(category);
        BlogPost savedPost = blogPostRepository.save(blogPost);
        try {
            emailService.sendNewPostNotification(savedPost);
        } catch (Exception e) {
            // Log the error but don't fail the operation
            System.err.println("Failed to send notification email: " + e.getMessage());
        }
        return savedPost;
    }

    @Override
    @Transactional(readOnly = true)
    public BlogPost getBlogPostById(Long postId) {
        return blogPostRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post not found with ID: " + postId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogPost> getActiveBlogPosts() {
        return blogPostRepository.findByDeletedAtIsNull();
    }

    @Override
    public BlogPost updateBlogPost(Long postId, BlogPost updatedBlogPost, Authentication authentication) {
        BlogPost existingPost = getBlogPostById(postId);

        User currentUser = userService.getCurrentUser(authentication);

        if (!existingPost.getAuthor().getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("You are not authorized to update this post");
        }

        // Do not allow updating the author, keep the original author
        updatedBlogPost.setAuthor(existingPost.getAuthor()); // Ensure author is not updated
        // Update title
        if (updatedBlogPost.getTitle() != null && !updatedBlogPost.getTitle().isBlank()) {
            existingPost.setTitle(updatedBlogPost.getTitle());
        }

        // Update content
        if (updatedBlogPost.getContent() != null && !updatedBlogPost.getContent().isBlank()) {
            existingPost.setContent(updatedBlogPost.getContent());
        }

        // Update slug if changed
        if (updatedBlogPost.getSlug() != null && !updatedBlogPost.getSlug().equals(existingPost.getSlug())) {
            if (blogPostRepository.existsBySlug(updatedBlogPost.getSlug())) {
                throw new IllegalArgumentException("Slug '" + updatedBlogPost.getSlug() + "' already exists");
            }
            existingPost.setSlug(updatedBlogPost.getSlug());
        }

        // Update category if changed
        if (updatedBlogPost.getCategory() != null) {
            Category newCategory = categoryRepository.findById(updatedBlogPost.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + updatedBlogPost.getCategory().getId()));
            existingPost.setCategory(newCategory);
        }

        return blogPostRepository.save(existingPost);
    }

    @Override
    public void deleteBlogPost(Long postId, Authentication authentication) {
        BlogPost post = getBlogPostById(postId);
        User currentUser = userService.getCurrentUser(authentication);
        if (currentUser.getRole() != User.Role.ADMIN &&
                !post.getAuthor().getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("You are not authorized to delete this post");
        }

        blogPostRepository.deleteById(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogPost> searchBlogPosts(String keyword) {
        return blogPostRepository.findByDeletedAtIsNullAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<Object[]> getPostCountByCategory() {
        return blogPostRepository.countPostsByCategory();
    }

}