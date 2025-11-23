package org.example.springaidemo.controllers;

import org.example.springaidemo.entities.Students;
import org.example.springaidemo.repositories.StudentRepo;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepo studentRepo;
    private final PasswordEncoder bcryptPasswordEncoder;

    @Autowired
    public StudentController(StudentRepo studentRepo, @Qualifier("passwordEncoder") PasswordEncoder bcryptPasswordEncoder) {
        this.studentRepo = studentRepo;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    @GetMapping("/get-all")
    public ResponseEntity getAllStudents() {
        List<Students> all = studentRepo.findAll();
        return new ResponseEntity(all, HttpStatus.OK);
    }

    @PostMapping("/add")
    public String addStudent(@RequestBody Students students) {
        students.setPassword(bcryptPasswordEncoder.encode(students.getPassword()));
        System.out.println(students.toString());
        Students save = studentRepo.save(students);
        if (save != null) {
            return "Student added successfully";
        } else {
            return "Failed to add student";
        }
    }

    @GetMapping("/find/{name}")
    public ResponseEntity findStudentByName(@PathVariable String name) {
        Students result = studentRepo.findByName(name);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
