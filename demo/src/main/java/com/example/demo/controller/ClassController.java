package com.example.demo.controller;

import com.example.demo.model.Class;
import com.example.demo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping
    public ResponseEntity<String> createClass(@RequestBody Class newClass) {
        try {
            classService.createClass(newClass);
            return ResponseEntity.status(HttpStatus.CREATED).body("Class created successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }
}
