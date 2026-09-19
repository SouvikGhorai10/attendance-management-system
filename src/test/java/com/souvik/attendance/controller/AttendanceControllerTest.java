package com.souvik.attendance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.souvik.attendance.model.AttendanceRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        // Seed one record before each test
        AttendanceRecord record = new AttendanceRecord("Souvik Ghorai", "STU001", LocalDate.now(), "PRESENT");
        mockMvc.perform(post("/api/attendance")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isCreated());
    }

    @Test
    void createAttendance_returnsCreatedRecord() throws Exception {
        AttendanceRecord record = new AttendanceRecord("Priya Das", "STU002", LocalDate.now(), "ABSENT");

        mockMvc.perform(post("/api/attendance")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value("STU002"))
                .andExpect(jsonPath("$.status").value("ABSENT"));
    }

    @Test
    void createAttendance_missingRequiredField_returnsBadRequest() throws Exception {
        String invalidJson = "{\"studentName\": \"\", \"studentId\": \"STU003\"}";

        mockMvc.perform(post("/api/attendance")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll_returnsSeededRecords() throws Exception {
        mockMvc.perform(get("/api/attendance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getByStudentId_returnsOnlyMatchingRecords() throws Exception {
        mockMvc.perform(get("/api/attendance/student/STU001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value("STU001"));
    }

    @Test
    void getAttendancePercentage_allPresent_returns100() throws Exception {
        mockMvc.perform(get("/api/attendance/student/STU001/percentage"))
                .andExpect(status().isOk())
                .andExpect(content().string("100.0"));
    }

    @Test
    void getById_unknownId_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/attendance/9999"))
                .andExpect(status().isNotFound());
    }
}
