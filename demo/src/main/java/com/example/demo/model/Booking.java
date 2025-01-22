package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Booking {
    private String memberName; // Name of the member booking the class
    private String className; // Name of the class being booked
    private LocalDate participationDate; // Date of the class participation
}
