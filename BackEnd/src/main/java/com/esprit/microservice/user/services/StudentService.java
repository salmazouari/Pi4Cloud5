package com.esprit.microservice.user.services;

import com.esprit.microservice.user.dto.StudentDto;
import com.esprit.microservice.user.entities.Biography;
import com.esprit.microservice.user.entities.Contact;
import com.esprit.microservice.user.entities.Resume;
import com.esprit.microservice.user.entities.Student;
import com.esprit.microservice.user.repositories.BiographyRepository;
import com.esprit.microservice.user.repositories.ContactRepository;
import com.esprit.microservice.user.repositories.ResumeRepository;
import com.esprit.microservice.user.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {


    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private BiographyRepository biographyRepository;


    @Autowired
    private ContactRepository contactRepository;


    @Autowired
    private ResumeRepository resumeRepository;

    public synchronized Collection<StudentDto> getAll() {
        return this.studentRepository.findAll().stream().map(StudentDto::new).collect(Collectors.toSet());
    }

    @Transactional
    public boolean create(final StudentDto studentDto ) {
        Student entity = studentDto.toEntity();

        if(entity.getBiography() != null) {
            Biography biography  = this.biographyRepository.save(entity.getBiography());
            entity.setBiography(biography);
        }

        if(entity.getContact() != null) {
            Contact contact = this.contactRepository.save(entity.getContact());
            entity.setContact(contact);
        }

        if(entity.getResume() != null) {
            Resume resume = this.resumeRepository.save(entity.getResume());
            entity.setResume(resume);
        }


        this.studentRepository.save(entity);
        return true;
    }

    @Transactional
    public boolean update(StudentDto student) {
        Student entity = student.toEntity();
        if (entity.getUserId() == null || !this.studentRepository.existsById(entity.getUserId())) return false;

        if(entity.getBiography() != null) {
            Biography biography  = this.biographyRepository.save(entity.getBiography());
            entity.setBiography(biography);
        }

        if(entity.getContact() != null) {
            Contact contact = this.contactRepository.save(entity.getContact());
            entity.setContact(contact);
        }

        if(entity.getResume() != null) {
            Resume resume = this.resumeRepository.save(entity.getResume());
            entity.setResume(resume);
        }

        return true;
    }

    @Transactional
    public boolean remove(StudentDto student) {
        Student entity = student.toEntity();
        if (entity.getUserId() == null || !this.studentRepository.existsById(entity.getUserId())) return false;

        this.studentRepository.delete(entity);
        this.biographyRepository.removeBiographyByStudentId(entity.getUserId());
        this.contactRepository.removeContactByStudentId(entity.getUserId());
        this.resumeRepository.removeResumeByStudentId(entity.getUserId());

        return true;
    }

    public StudentDto getById(Long studentID) {
        Student entity = this.studentRepository.findById(studentID).orElse(null);
        return entity == null ? null : new StudentDto(entity) ;
    }
}
