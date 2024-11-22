package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void init() {
        // Initialize a User object for testing
        user = new User()
                .setId(101L)
                .setEmail("alex.johnson@example.com")
                .setFirstName("Alex")
                .setLastName("Johnson")
                .setPassword("securePass!")
                .setAdmin(true);
    }

    @Test
    void testDeleteUser() {
        // Arrange: Define a user ID to delete
        Long userId = 101L;

        // Act: Call the delete method
        userService.delete(userId);

        // Assert: Verify that the repository's deleteById method was called with the correct ID
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testFindById_UserExists() {
        // Arrange: Mock repository to return the user
        Long userId = 101L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act: Call the findById method
        User foundUser = userService.findById(userId);

        // Assert: Verify the returned user matches the mocked one
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(userId);
        assertThat(foundUser.getEmail()).isEqualTo("alex.johnson@example.com");

        // Verify the repository's findById method was called once
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_UserNotFound() {
        // Arrange: Mock repository to simulate no user found
        Long userId = 101L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act: Call the findById method
        User foundUser = userService.findById(userId);

        // Assert: Verify the method returns null if the user is not found
        assertThat(foundUser).isNull();

        // Verify the repository's findById method was called once
        verify(userRepository, times(1)).findById(userId);
    }
}
