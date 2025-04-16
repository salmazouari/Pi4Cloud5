package com.example.skillbridge.repositories;

import com.example.skillbridge.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByUser_UserId(Long userId);
    List<Comment> findByDeletedAtIsNull();
}