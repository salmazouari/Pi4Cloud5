package com.esprit.microservice.user.repositories;

import com.esprit.microservice.user.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {


    @Modifying
    @Query("delete from Resume resume where resume.student.userId = :id")
    void removeResumeByStudentId(@Param("id") Long id);
}
