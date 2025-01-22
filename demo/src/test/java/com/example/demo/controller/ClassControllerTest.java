package com.example.demo.controller;

import com.example.demo.model.Class;
import com.example.demo.service.ClassService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClassController.class)
class ClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassService classService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateClass_WithValidInput_ShouldReturnCreated() throws Exception {
        // Arrange
        Class validClass = Class.builder()
                .name("Yoga")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(10))
                .startTime(LocalTime.of(10, 0))
                .duration(60)
                .capacity(10)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/classes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validClass)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateClass_WithInvalidCapacity_ShouldReturnBadRequest() throws Exception {
        // Arrange
        Class invalidClass = Class.builder()
                .name("Pilates")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(10))
                .startTime(LocalTime.of(14, 0))
                .duration(60)
                .capacity(0) // Invalid capacity
                .build();

        Mockito.doThrow(new IllegalArgumentException("Capacity must be at least 1"))
                .when(classService).createClass(Mockito.any(Class.class));

        // Act & Assert
        mockMvc.perform(post("/api/classes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidClass)))
                .andExpect(status().isBadRequest());
    }
}
