package com.openclassrooms.starterjwt.security.jwt.services;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {

        @Test
        public void shouldBeEqualToItself() {
                // Arrange: Create an instance of UserDetailsImpl
                UserDetailsImpl user = UserDetailsImpl.builder()
                                .id(101L)
                                .username("alice@example.com")
                                .firstName("Alice")
                                .lastName("Wonderland")
                                .admin(false)
                                .password("securePassword")
                                .build();

                // Act & Assert: Verify the instance is equal to itself
                assertTrue(user.equals(user), "An instance should always be equal to itself.");
        }

        @Test
        public void shouldNotBeEqualToNull() {
                // Arrange: Create an instance of UserDetailsImpl
                UserDetailsImpl user = UserDetailsImpl.builder()
                                .id(102L)
                                .username("bob@example.com")
                                .firstName("Bob")
                                .lastName("Builder")
                                .admin(false)
                                .password("builder123")
                                .build();

                // Act & Assert: Verify comparison with null returns false
                assertFalse(user.equals(null), "An instance should not be equal to null.");
        }

        @Test
        public void shouldNotBeEqualToDifferentClass() {
                // Arrange: Create an instance of UserDetailsImpl
                UserDetailsImpl user = UserDetailsImpl.builder()
                                .id(103L)
                                .username("charlie@example.com")
                                .firstName("Charlie")
                                .lastName("Chocolate")
                                .admin(false)
                                .password("goldenTicket")
                                .build();

                // Act: Compare with an object of a different class
                String differentClassObject = "IAmNotAUserDetails";

                // Assert: Verify comparison with a different class object returns false
                assertFalse(user.equals(differentClassObject),
                                "An instance should not be equal to an object of a different class.");
        }

        @Test
        public void shouldNotBeEqualIfIdsAreDifferent() {
                // Arrange: Create two instances of UserDetailsImpl with different IDs
                UserDetailsImpl user1 = UserDetailsImpl.builder()
                                .id(104L)
                                .username("dave@example.com")
                                .firstName("Dave")
                                .lastName("Explorer")
                                .admin(false)
                                .password("adventure")
                                .build();

                UserDetailsImpl user2 = UserDetailsImpl.builder()
                                .id(105L)
                                .username("eve@example.com")
                                .firstName("Eve")
                                .lastName("Hacker")
                                .admin(false)
                                .password("hackMe")
                                .build();

                // Act & Assert: Verify instances with different IDs are not equal
                assertFalse(user1.equals(user2),
                                "Two instances with different IDs should not be equal.");
        }

        @Test
        public void shouldCorrectlyIdentifyAdminStatus() {
                // Arrange: Create one admin user and one regular user
                UserDetailsImpl adminUser = UserDetailsImpl.builder()
                                .id(106L)
                                .username("admin@platform.com")
                                .firstName("Super")
                                .lastName("Admin")
                                .admin(true)
                                .password("rootAccess")
                                .build();

                UserDetailsImpl regularUser = UserDetailsImpl.builder()
                                .id(107L)
                                .username("jane@platform.com")
                                .firstName("Jane")
                                .lastName("Doe")
                                .admin(false)
                                .password("password123")
                                .build();

                // Act & Assert: Verify the admin property
                assertTrue(adminUser.getAdmin(), "Admin property should be true for an admin user.");
                assertFalse(regularUser.getAdmin(), "Admin property should be false for a regular user.");
        }
}
