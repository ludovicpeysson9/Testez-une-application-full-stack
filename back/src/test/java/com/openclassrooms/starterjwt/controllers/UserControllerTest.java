package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void init() {
        // Initialize User and UserDto objects for testing
        user = User.builder()
                .id(10L)
                .email("alice@example.com")
                .firstName("Alice")
                .lastName("Wonderland")
                .password("passwordSecure!")
                .admin(false)
                .build();

        userDto = new UserDto(10L, "alice@example.com", "Wonderland", "Alice", false, "passwordSecure!",
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void testFindById_Success() {
        // Arrange: Mock UserService and UserMapper behavior
        when(userService.findById(10L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act: Call the findById method
        ResponseEntity<?> response = userController.findById("10");

        // Assert: Validate response
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    public void testFindById_NotFound() {
        // Arrange: Mock UserService to return null
        when(userService.findById(anyLong())).thenReturn(null);

        // Act: Call the findById method with a non-existent ID
        ResponseEntity<?> response = userController.findById("10");

        // Assert: Validate Not Found response
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testDeleteUser_Success() {
        // Arrange: Mock UserService and UserDetails
        when(userService.findById(10L)).thenReturn(user);
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("alice@example.com");

        // Simulate authenticated user
        Authentication auth = new UsernamePasswordAuthenticationToken(mockUserDetails, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Act: Call the delete method
        ResponseEntity<?> response = userController.save("10");

        // Assert: Validate success response and service invocation
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(userService, times(1)).delete(10L);
    }

    @Test
    public void testDeleteUser_Unauthorized() {
        // Arrange: Mock UserService and UserDetails with mismatched email
        when(userService.findById(10L)).thenReturn(user);
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("bob@example.com");

        // Simulate unauthorized user
        Authentication auth = new UsernamePasswordAuthenticationToken(mockUserDetails, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Act: Call the delete method
        ResponseEntity<?> response = userController.save("10");

        // Assert: Validate Unauthorized response and no service invocation
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
        verify(userService, never()).delete(anyLong());
    }

    @Test
    public void testFindById_InvalidIdFormat() {
        // Act: Call the findById method with an invalid ID format
        ResponseEntity<?> response = userController.findById("invalidId");

        // Assert: Validate Bad Request response
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }
}
