package com.example.demo.service;

import com.example.demo.model.Class;
import com.example.demo.repository.ClassRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.List;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public Class createClass(Class classObj) {
        if (classObj.getCapacity() <= 0) {
            throw new IllegalArgumentException("Class capacity must be greater than 0");
        }

        if (classObj.getEndDate().isBefore(classObj.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        return classRepository.save(classObj);
    }

    public List<Class> getAllClasses() {
        return new ArrayList<>(classRepository.findAll().values());
    }
}
