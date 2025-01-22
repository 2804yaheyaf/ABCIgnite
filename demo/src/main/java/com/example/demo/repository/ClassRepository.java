package com.example.demo.repository;

import com.example.demo.model.Class;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ClassRepository {
    private final ConcurrentHashMap<String, Class> classes = new ConcurrentHashMap<>();

    public Class save(Class classObj) {
        classes.put(classObj.getName(), classObj);
        return classObj;
    }

    public Class findByName(String name) {
        return classes.get(name);
    }

    public ConcurrentHashMap<String, Class> findAll() {
        return classes;
    }

    @PostConstruct
    private void initializeData() {
        Class yogaClass = Class.builder()
                .name("Yoga")
                .startDate(LocalDate.of(2025, 1, 20))
                .endDate(LocalDate.of(2025, 2, 1))
                .startTime(LocalTime.of(10, 0))
                .duration(60)
                .capacity(10)
                .build();

        Class pilatesClass = Class.builder()
                .name("Pilates")
                .startDate(LocalDate.of(2025, 1, 22))
                .endDate(LocalDate.of(2025, 2, 5))
                .startTime(LocalTime.of(14, 0))
                .duration(45)
                .capacity(15)
                .build();

        Class ZumbaClass = Class.builder()
                .name("Zumba")
                .startDate(LocalDate.of(2025, 1, 20))
                .endDate(LocalDate.of(2025, 2, 7))
                .startTime(LocalTime.of(16, 0))
                .duration(45)
                .capacity(20)
                .build();

        save(yogaClass);
        save(pilatesClass);
        save(ZumbaClass);

        System.out.println("Initial classes loaded: " + classes);
    }
}
