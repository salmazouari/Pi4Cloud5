package com.example.skillbridge.services;

import com.example.skillbridge.exceptions.ResourceNotFoundException;
import com.example.skillbridge.models.Comment;
import com.example.skillbridge.models.BlogPost;
import com.example.skillbridge.models.User;
import com.example.skillbridge.repositories.CommentRepository;
import com.example.skillbridge.repositories.BlogPostRepository;
import com.example.skillbridge.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public Comment updateComment(Long commentId, Comment updatedComment) {
        Comment existingComment = getCommentById(commentId);
        existingComment.setContent(updatedComment.getContent());
        return commentRepository.save(existingComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        comment.setDeletedAt(Instant.now());
        commentRepository.save(comment); // Soft delete
    }
}