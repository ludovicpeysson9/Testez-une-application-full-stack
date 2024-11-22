package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        // Clean the DataBase before each test
        userRepository.deleteAll();
    }

    @Test
    public void testAuthenticateUser_Success() throws Exception {
        // Arrange : creation of a user in DataBase 
        User user = new User("toto@test.com", "test", "toto", passwordEncoder.encode("toto123!"), false);
        userRepository.save(user);

        // Act : POST request to connect
        ResultActions result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\": \"toto@test.com\", \"password\": \"toto123!\" }"));

        // Assert : check if authentication is OK
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("toto@test.com"))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value("toto"))
                .andExpect(jsonPath("$.lastName").value("test"))
                .andExpect(jsonPath("$.admin").value(false));
    }

    @Test
    public void testAuthenticateUser_Failure() throws Exception {
        // Act : attempt to connect with wrong logs
        ResultActions result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\": \"wrong@example.com\", \"password\": \"wrongpassword\" }"));

        // Assert : check that authentication is a failure
        result.andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        // Act : POST request to register a User
        ResultActions result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\": \"toto@test.com\", \"password\": \"toto123!\", \"firstName\": \"toto\", \"lastName\": \"test\" }"));

        // Assert : check if the User is well registered
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));

        // Also check in DataBase
        User savedUser = userRepository.findByEmail("toto@test.com").orElse(null);
        assertNotNull(savedUser, "L'utilisateur devrait être enregistré dans la base de données");
    }

    @Test
    public void testRegisterUser_EmailAlreadyTaken() throws Exception {
        // Arrange : creation of a User with an already existing email address
        User existingUser = new User("existinguser@test.com", "test", "toto", passwordEncoder.encode("toto123!"), false);
        userRepository.save(existingUser);

        // Act : POST request with an already existing email address
        ResultActions result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\": \"existinguser@test.com\", \"password\": \"toto123!\", \"firstName\": \"toto\", \"lastName\": \"test\" }"));

        // Assert : check if the recording is a failure
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Email is already taken!"));
    }
}
