package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.SpringBootSecurityJwtApplication;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SpringBootSecurityJwtApplication.class)
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    private Session session;
    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        sessionRepository.deleteAll();
        teacherRepository.deleteAll();
        userRepository.deleteAll();

        // Create and save a teacher

        teacher = teacherRepository.save(new Teacher().setFirstName("toto").setLastName("test"));


        // Initialize a session (not yet saved)
        session = new Session()
            .setName("Yoga Session")
            .setDescription("A yoga session to relax.")
            .setDate(new Date())
            .setTeacher(teacher);

    }

    @Test
    @WithMockUser(username = "testUser", roles = { "USER" }) // Simulate an authenticated user
    public void testFindById_Success() throws Exception {
        // Save the session
        session = sessionRepository.save(session);

        // Perform GET request to find the session by ID and validate the response
        mockMvc.perform(get("/api/session/" + session.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Yoga Session"))
                .andExpect(jsonPath("$.teacher_id").value(teacher.getId()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "USER" }) // Simulate an authenticated user
    public void testFindById_NotFound() throws Exception {
        // Perform GET request for a non-existent session and validate the response
        mockMvc.perform(get("/api/session/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "USER" }) // Simulate an authenticated user
    public void testCreate_Success() throws Exception {
        // Perform POST request to create a new session and validate the response
        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Yoga Session\", \"description\": \"A relaxing yoga session\", \"date\": \"2024-11-15T10:00:00\", \"teacher_id\": " + teacher.getId() + " }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("New Yoga Session"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "USER" }) // Simulate an authenticated user
    public void testUpdate_Success() throws Exception {
        // Save the session to update
        session = sessionRepository.save(session);

        // Perform PUT request to update the session and validate the response
        mockMvc.perform(put("/api/session/" + session.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Yoga Session\", \"description\": \"Updated description\", \"date\": \"2024-11-15T10:00:00\", \"teacher_id\": " + teacher.getId() + " }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Yoga Session"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "USER" }) // Simulate an authenticated user
    public void testDelete_Success() throws Exception {
        // Save the session to delete
        session = sessionRepository.save(session);

        // Perform DELETE request to remove the session and validate the response
        mockMvc.perform(delete("/api/session/" + session.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "USER" }) // Simulate an authenticated user
    public void testParticipate_Success() throws Exception {
        // Save a session and a user
        session = sessionRepository.save(session);
        User user = userRepository.save(new User("toto@test.com", "test", "toto", "toto123!", false));

        // Perform POST request to add a participant to the session and validate the response
        mockMvc.perform(post("/api/session/" + session.getId() + "/participate/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
