package com.example.skillbridge.services;

import com.example.skillbridge.exceptions.ResourceNotFoundException;
import com.example.skillbridge.models.Comment;
import com.example.skillbridge.models.BlogPost;
import com.example.skillbridge.models.User;
import com.example.skillbridge.repositories.CommentRepository;
import com.example.skillbridge.repositories.BlogPostRepository;
import com.example.skillbridge.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final BadWordFilterService badWordFilterService; // ✅ Add this line
    private final UserService userService; // Add this dependency

    @Override
    public Comment createComment(Comment comment) {
        // Validate post exists
        BlogPost post = blogPostRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        // Validate user exists
        User user = userRepository.findById(comment.getUser().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 🚫 Check for bad words
        if (badWordFilterService.containsBadWords(comment.getContent())) {
            throw new IllegalArgumentException("Comment contains inappropriate language");
        }

        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByUser_UserId(userId);
    }

    @Override
    public Comment updateComment(Long commentId, Comment updatedComment, Authentication authentication) {
        Comment existingComment = getCommentById(commentId);
        User currentUser = userService.getCurrentUser(authentication);
        // Authorization check
        if (!existingComment.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("You are not authorized to update this comment");
        }
        existingComment.setContent(updatedComment.getContent());
        return commentRepository.save(existingComment);
    }

    @Override
    public void deleteComment(Long commentId, Authentication authentication) {
        Comment comment = getCommentById(commentId);
        User currentUser = userService.getCurrentUser(authentication);
        if (currentUser.getRole() != User.Role.ADMIN &&
                !comment.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("You are not authorized to delete this comment");
        }
        comment.setDeletedAt(Instant.now());
        commentRepository.save(comment); // Soft delete
    }
}