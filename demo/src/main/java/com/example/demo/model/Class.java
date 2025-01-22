package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class Class {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private int duration; // in minutes
    private int capacity;
}
