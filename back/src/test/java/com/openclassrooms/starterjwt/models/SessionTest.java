package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    @Test
    public void shouldVerifyEqualsAndHashCode() {
        // Arrange: Create two different session objects
        Session firstSession = new Session();
        firstSession.setId(101L).setName("Morning Yoga");

        Session secondSession = new Session();
        secondSession.setId(202L).setName("Evening Meditation");

        // Act & Assert: Verify that sessions with different IDs are not equal
        assertNotEquals(firstSession, secondSession, "Sessions with different IDs should not be equal");

        // Arrange: Update the second session's ID to match the first session
        secondSession.setId(101L);

        // Assert: Verify that sessions with the same ID are now equal
        assertEquals(firstSession, secondSession, "Sessions with matching IDs should be considered equal");
    }

    @Test
    public void shouldSetCreatedAtAndUpdatedAtCorrectly() {
        // Arrange: Initialize a new session instance
        Session session = new Session();

        // Act: Set the createdAt timestamp
        LocalDateTime creationTimestamp = LocalDateTime.now();
        session.setCreatedAt(creationTimestamp);

        // Assert: Ensure the createdAt timestamp is correctly set
        assertEquals(creationTimestamp, session.getCreatedAt(), "The createdAt timestamp should match the assigned value");

        // Act: Set a different updatedAt timestamp
        LocalDateTime updateTimestamp = LocalDateTime.now().plusDays(2);
        session.setUpdatedAt(updateTimestamp);

        // Assert: Ensure the updatedAt timestamp is correctly set
        assertEquals(updateTimestamp, session.getUpdatedAt(), "The updatedAt timestamp should match the assigned value");
    }

    @Test
    public void shouldVerifyCanEqual() {
        // Arrange: Create two session instances
        Session sessionA = new Session();
        Session sessionB = new Session();

        // Act & Assert: Verify that sessionA can be compared to sessionB
        assertTrue(sessionA.canEqual(sessionB), "sessionA should be able to compare with sessionB");
    }
}
