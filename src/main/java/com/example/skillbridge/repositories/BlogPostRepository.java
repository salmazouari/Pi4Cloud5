package com.example.skillbridge.repositories;

import com.example.skillbridge.models.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    boolean existsBySlug(String slug);
    List<BlogPost> findByDeletedAtIsNull();
}