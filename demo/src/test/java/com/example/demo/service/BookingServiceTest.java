package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.model.Class;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    private BookingRepository bookingRepository;
    private ClassRepository classRepository;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingRepository = Mockito.mock(BookingRepository.class);
        classRepository = Mockito.mock(ClassRepository.class);
        bookingService = new BookingService(bookingRepository, classRepository);
    }

    @Test
    void testBookClass_Success() {
        // Arrange
        Class yogaClass = Class.builder()
                .name("Yoga")
                .startDate(LocalDate.of(2025, 1, 20))
                .endDate(LocalDate.of(2025, 2, 1))
                .startTime(null)
                .duration(60)
                .capacity(10)
                .build();

        Booking booking = Booking.builder()
                .memberName("John Doe")
                .className("Yoga")
                .participationDate(LocalDate.of(2025, 1, 25))
                .build();

        when(classRepository.findByName("Yoga")).thenReturn(yogaClass);
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        bookingService.bookClass(booking);

        // Assert
        verify(bookingRepository).save(booking);
    }

    @Test
    void testBookClass_ClassDoesNotExist() {
        // Arrange
        Booking booking = Booking.builder()
                .memberName("John Doe")
                .className("Nonexistent")
                .participationDate(LocalDate.of(2025, 1, 25))
                .build();

        when(classRepository.findByName("Nonexistent")).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.bookClass(booking);
        });

        // Verify exception message
        assertThrows(IllegalArgumentException.class, () -> bookingService.bookClass(booking));
        verify(bookingRepository, never()).save(booking);
    }

    @Test
    void testBookClass_ParticipationDateInPast() {
        // Arrange
        Class yogaClass = Class.builder()
                .name("Yoga")
                .startDate(LocalDate.of(2025, 1, 20))
                .endDate(LocalDate.of(2025, 2, 1))
                .startTime(null)
                .duration(60)
                .capacity(10)
                .build();

        Booking booking = Booking.builder()
                .memberName("John Doe")
                .className("Yoga")
                .participationDate(LocalDate.of(2024, 12, 31)) // Past date
                .build();

        when(classRepository.findByName("Yoga")).thenReturn(yogaClass);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.bookClass(booking);
        });

        assertEquals("Participation date must be in the future.", exception.getMessage());
        verify(bookingRepository, never()).save(booking);
    }

    @Test
    void testBookClass_ClassFullyBooked() {
        // Arrange
        Class yogaClass = Class.builder()
                .name("Yoga")
                .startDate(LocalDate.of(2025, 1, 20))
                .endDate(LocalDate.of(2025, 2, 1))
                .startTime(null)
                .duration(60)
                .capacity(1) // Class capacity is 1
                .build();

        Booking existingBooking = Booking.builder()
                .memberName("Jane Smith")
                .className("Yoga")
                .participationDate(LocalDate.of(2025, 1, 25))
                .build();

        Booking newBooking = Booking.builder()
                .memberName("John Doe")
                .className("Yoga")
                .participationDate(LocalDate.of(2025, 1, 25)) // Same date as existing booking
                .build();

        when(classRepository.findByName("Yoga")).thenReturn(yogaClass);
        when(bookingRepository.findAll()).thenReturn(List.of(existingBooking));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.bookClass(newBooking);
        });

        assertEquals("Class is fully booked for the selected date.", exception.getMessage());
        verify(bookingRepository, never()).save(newBooking);
    }
}
