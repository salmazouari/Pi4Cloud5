package com.esprit.microservice.user.dto;

import com.esprit.microservice.user.Speciality;
import com.esprit.microservice.user.entities.Experience;
import com.esprit.microservice.user.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Speciality speciality;
    private BiographyDto biography;
    private ResumeDto resume;
    private ContactDto contact;
    private Set<ExperienceDto> experiences;


    public StudentDto(final Student student) {
        this.id = student.getUserId();
        this.username = student.getUsername();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.speciality = student.getSpeciality();
        this.biography = student.getBiography() == null ? null : new BiographyDto( student.getBiography() ) ;
        this.resume = student.getResume() == null ? null : new ResumeDto( student.getResume() ) ;
        this.contact = student.getContact() == null ? null : new ContactDto( student.getContact() ) ;
        if (student.getExperiences() != null) {
            this.experiences = new HashSet<>();
            ExperienceDto dto;
            for (Experience experience : student.getExperiences()) {
                dto = new ExperienceDto(experience);
                this.experiences.add(dto);
            }
        }
    }


    public StudentDto(final Student student, final Set<Experience> experiences) {
        this.id = student.getUserId();
        this.username = student.getUsername();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.speciality = student.getSpeciality();
        this.biography = student.getBiography() == null ? null : new BiographyDto( student.getBiography() ) ;
        this.resume = student.getResume() == null ? null : new ResumeDto( student.getResume() ) ;
        this.contact = student.getContact() == null ? null : new ContactDto( student.getContact() ) ;
        if (experiences != null) {
            ExperienceDto dto;
            for (Experience experience : experiences) {
                dto = new ExperienceDto(experience);
                this.experiences.add(dto);
            }
        }
    }


    public Student toEntity() {

        Student student = new Student();
        student.setUserId(this.id);
        student.setFirstName(this.firstName);
        student.setUsername(this.username);
        student.setLastName(this.lastName);
        student.setSpeciality(this.speciality);
        student.setBiography(this.biography == null ? null : this.biography.toEntity());
        student.setResume(this.resume == null ? null : this.resume.toEntity());
        student.setContact(this.contact == null ? null : this.contact.toEntity());
        if (this.experiences != null)
           student.setExperiences(this.experiences.stream().map(ExperienceDto::toEntity).collect(Collectors.toSet()));

        return student;
    }
}
