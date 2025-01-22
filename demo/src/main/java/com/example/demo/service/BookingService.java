package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.model.Class;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
// import java.util.stream.Collectors;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ClassRepository classRepository;

    public BookingService(BookingRepository bookingRepository, ClassRepository classRepository) {
        this.bookingRepository = bookingRepository;
        this.classRepository = classRepository;
    }

    public void bookClass(Booking booking) {
        // Validate the booking
        Class classToBook = classRepository.findByName(booking.getClassName());
        if (classToBook == null) {
            throw new IllegalArgumentException("Class not found: " + booking.getClassName());
        }

        if (booking.getParticipationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Participation date must be in the future.");
        }

        long currentBookings = bookingRepository.findAll().stream()
                .filter(b -> b.getClassName().equals(booking.getClassName()) &&
                        b.getParticipationDate().equals(booking.getParticipationDate()))
                .count();

        if (currentBookings >= classToBook.getCapacity()) {
            throw new IllegalArgumentException("Class is fully booked for the selected date.");
        }

        // Save the booking
        bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> searchBookings(String memberName, String startDate, String endDate) {
        // Parse date range if provided
        LocalDate start = (startDate != null) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null) ? LocalDate.parse(endDate) : null;

        // adding validation to ensure start date is not after end date
        if (start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        // Filter bookings
        return bookingRepository.findAll().stream()
                .filter(booking -> {
                    boolean matchesMember = (memberName == null
                            || booking.getMemberName().equalsIgnoreCase(memberName));
                    boolean matchesDate = (start == null || end == null) ||
                            (booking.getParticipationDate().compareTo(start) >= 0
                                    && booking.getParticipationDate().compareTo(end) <= 0);
                    return matchesMember && matchesDate;
                })
                .collect(Collectors.toList());
    }

}
