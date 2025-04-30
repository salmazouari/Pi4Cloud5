package com.esprit.microservice.user.repositories;

import com.esprit.microservice.user.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {


    @Modifying

    @Query("delete from Contact contact where contact.student.userId = :id")
    void removeContactByStudentId(@Param("id") Long id);
}
