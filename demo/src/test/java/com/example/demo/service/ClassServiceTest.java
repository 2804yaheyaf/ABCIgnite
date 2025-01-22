package com.example.demo.service;

import com.example.demo.model.Class;
import com.example.demo.repository.ClassRepository;
// import com.example.demo.service.ClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;

class ClassServiceTest {

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private ClassService classService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClass_WithValidClass_ShouldSaveSuccessfully() {
        // Arrange
        Class validClass = Class.builder()
                .name("Yoga")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(10))
                .startTime(LocalTime.of(10, 0))
                .duration(60)
                .capacity(10)
                .build();

        // Act
        classService.createClass(validClass);

        // Assert
        verify(classRepository).save(validClass);
    }

    @Test
    void testCreateClass_WithInvalidCapacity_ShouldThrowException() {
        Class invalidClass = Class.builder()
                .name("Yoga")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(10))
                .startTime(LocalDate.now().atTime(9, 0).toLocalTime())
                .capacity(0)
                .build();

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> classService.createClass(invalidClass));
    }

    @Test
    void testCreateClass_WithPastEndDate_ShouldThrowException() {
        // Arrange
        Class invalidClass = Class.builder()
                .name("Yoga")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().minusDays(1))
                .startTime(LocalDate.now().atTime(9, 0).toLocalTime())
                .capacity(10) // Invalid capacity
                .build();

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> classService.createClass(invalidClass));
    }
}
