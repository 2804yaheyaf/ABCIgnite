package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> bookClass(@RequestBody Booking booking) {
        try {
            bookingService.bookClass(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // @GetMapping
    // public ResponseEntity<?> getAllBookings() {
    // return ResponseEntity.ok(bookingService.getAllBookings());
    // }

    // GET endpoint to search bookings
    @GetMapping
    public ResponseEntity<List<Booking>> searchBookings(
            @RequestParam(required = false) String memberName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<Booking> results = bookingService.searchBookings(memberName, startDate, endDate);
        return ResponseEntity.ok(results);
    }
}
