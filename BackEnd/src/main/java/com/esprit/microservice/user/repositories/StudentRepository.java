package com.esprit.microservice.user.repositories;

import com.esprit.microservice.user.dto.StudentDto;
import com.esprit.microservice.user.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {



    @Query(
            "select distinct new com.esprit.microservice.user.dto.StudentDto(st) " +
                    "from Student st " +
                    "left join fetch st.experiences " +
                    "left join fetch st.biography " +
                    "left join fetch st.contact " +
                    "left join fetch st.resume"
    )
    Set<StudentDto> getAll();

}
