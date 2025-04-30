package com.esprit.microservice.user.restController;

import com.esprit.microservice.user.dto.StudentDto;
import com.esprit.microservice.user.services.StudentService;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("get-all")
    public Collection<StudentDto> getAll(){
        return this.studentService.getAll();
    }


    @PostMapping("create")
    public boolean create( @RequestBody final StudentDto student ){
        return this.studentService.create(student);
    }


    @GetMapping("get-by-id")
    public StudentDto getById( @RequestParam("id") Long studentID){
        return this.studentService.getById(studentID);
    }


    @PutMapping("update")
    public boolean update( @RequestBody final StudentDto student ){
        return this.studentService.update(student);
    }


    @DeleteMapping("remove")
    public boolean remove( @RequestBody final StudentDto student ){
        return this.studentService.remove(student);
    }
}
