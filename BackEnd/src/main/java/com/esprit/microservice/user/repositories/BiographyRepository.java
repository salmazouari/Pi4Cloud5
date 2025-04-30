package com.esprit.microservice.user.repositories;

import com.esprit.microservice.user.entities.Biography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BiographyRepository extends JpaRepository<Biography, Long> {



    @Modifying
    @Query("delete from Biography bio where bio.student.userId = :id")
    void removeBiographyByStudentId(@Param("id") Long id);
}
