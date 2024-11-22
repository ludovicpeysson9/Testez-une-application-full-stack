package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.SpringBootSecurityJwtApplication;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SpringBootSecurityJwtApplication.class)
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        // Clear database to ensure consistent test results
        sessionRepository.deleteAll();
        teacherRepository.deleteAll();

        // Add a sample teacher for testing
        teacher = teacherRepository.save(Teacher.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Test
    @WithMockUser(username = "authenticatedUser", roles = {"USER"}) // Simulate an authenticated user
    public void testFindById_Success() throws Exception {
        // Perform GET request to fetch teacher by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/" + teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(teacher.getId().intValue())))
                .andExpect(jsonPath("$.lastName", is("Johnson")))
                .andExpect(jsonPath("$.firstName", is("Alice")));
    }

    @Test
    @WithMockUser(username = "authenticatedUser", roles = {"USER"}) // Simulate an authenticated user
    public void testFindById_NotFound() throws Exception {
        // Perform GET request with a non-existent ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "authenticatedUser", roles = {"USER"}) // Simulate an authenticated user
    public void testFindById_InvalidIdFormat() throws Exception {
        // Perform GET request with invalid ID format
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "authenticatedUser", roles = {"USER"}) // Simulate an authenticated user
    public void testFindAll_Success() throws Exception {
        // Perform GET request to fetch all teachers
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(teacher.getId().intValue())))
                .andExpect(jsonPath("$[0].lastName", is("Johnson")))
                .andExpect(jsonPath("$[0].firstName", is("Alice")));
    }

    @Test
    @WithMockUser(username = "authenticatedUser", roles = {"USER"}) // Simulate an authenticated user
    public void testFindAll_EmptyList() throws Exception {
        // Clear teachers to test empty list response
        sessionRepository.deleteAll();
        teacherRepository.deleteAll();

        // Perform GET request to fetch teachers when none exist
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(0)));
    }
}
