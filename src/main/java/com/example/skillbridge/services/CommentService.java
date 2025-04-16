package com.example.skillbridge.services;

import com.example.skillbridge.models.Comment;
import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    Comment getCommentById(Long commentId);
    List<Comment> getCommentsByPost(Long postId);
    List<Comment> getCommentsByUser(Long userId);
    Comment updateComment(Long commentId, Comment comment);
    void deleteComment(Long commentId);
}