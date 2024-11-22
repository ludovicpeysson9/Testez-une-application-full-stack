package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User primaryUser;
    private User secondaryUser;

    @BeforeEach
    public void initializeUsers() {
        // Arrange: Initialize User objects with predefined values
        primaryUser = new User(1001L, "alpha@domain.com", "Smith", "Alice", "securePass123", false,
                LocalDateTime.now(), LocalDateTime.now());
        secondaryUser = new User(1002L, "beta@domain.com", "Johnson", "Bob", "anotherPass456", true,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void shouldReturnCorrectStringRepresentation() {
        // Arrange: Prepare the expected string output
        String expected = "User(id=1001, email=alpha@domain.com, lastName=Smith, firstName=Alice, password=securePass123, admin=false, createdAt="
                + primaryUser.getCreatedAt() + ", updatedAt=" + primaryUser.getUpdatedAt() + ")";

        // Act: Obtain the actual string output from toString
        String actual = primaryUser.toString();

        // Assert: Compare the actual output to the expected string
        assertEquals(expected, actual, "The toString method should return the correct string representation of the User object");
    }

    @Test
    public void shouldHaveConsistentHashCodeForSameAttributes() {
        // Arrange: Create another User object with the same attributes as primaryUser
        User duplicateUser = new User(1001L, "alpha@domain.com", "Smith", "Alice", "securePass123", false,
                primaryUser.getCreatedAt(), primaryUser.getUpdatedAt());

        // Assert: Verify that hashCodes are identical for users with the same attributes
        assertEquals(primaryUser.hashCode(), duplicateUser.hashCode(),
                "HashCodes should be identical for users with the same attributes");

        // Assert: Verify that hashCodes differ for users with different IDs
        assertNotEquals(primaryUser.hashCode(), secondaryUser.hashCode(),
                "HashCodes should differ for users with different IDs");
    }

    @Test
    public void shouldBeEqualForSameAttributes() {
        // Arrange: Create another User object with the same attributes as primaryUser
        User duplicateUser = new User(1001L, "alpha@domain.com", "Smith", "Alice", "securePass123", false,
                primaryUser.getCreatedAt(), primaryUser.getUpdatedAt());

        // Act & Assert: Verify that primaryUser is equal to another User with the same attributes
        assertTrue(primaryUser.equals(duplicateUser), "Users with the same ID and attributes should be considered equal");

        // Act & Assert: Verify that primaryUser is not equal to secondaryUser (different IDs)
        assertFalse(primaryUser.equals(secondaryUser), "Users with different IDs should not be considered equal");

        // Act & Assert: Verify that primaryUser is not equal to null
        assertFalse(primaryUser.equals(null), "A User should not be equal to null");

        // Act & Assert: Verify that primaryUser is not equal to an object of another type
        assertFalse(primaryUser.equals(new Object()), "A User should not be equal to an object of another type");
    }
}
