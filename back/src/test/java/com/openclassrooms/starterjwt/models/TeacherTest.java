package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {

    private Teacher teacherAlpha;
    private Teacher teacherBeta;

    @BeforeEach
    public void initializeTeachers() {
        // Arrange: Initialize Teacher objects with predefined values
        teacherAlpha = new Teacher(101L, "Johnson", "Alice", LocalDateTime.now(), LocalDateTime.now());
        teacherBeta = new Teacher(202L, "Brown", "Michael", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void shouldVerifyToStringContent() {
        // Act: Call the toString method on teacherAlpha
        String output = teacherAlpha.toString();

        // Assert: Verify the output contains the expected values
        assertTrue(output.contains("id=101"), "The toString output should contain 'id=101'");
        assertTrue(output.contains("lastName=Johnson"), "The toString output should contain 'lastName=Johnson'");
        assertTrue(output.contains("firstName=Alice"), "The toString output should contain 'firstName=Alice'");
        assertTrue(output.contains("createdAt="), "The toString output should contain 'createdAt='");
        assertTrue(output.contains("updatedAt="), "The toString output should contain 'updatedAt='");
    }

    @Test
    public void shouldHaveSameHashCodeForSameId() {
        // Arrange: Create another Teacher object with the same ID as teacherAlpha
        Teacher duplicateTeacher = new Teacher(101L, "Johnson", "Alice", teacherAlpha.getCreatedAt(), teacherAlpha.getUpdatedAt());

        // Assert: Verify that hashCodes are identical
        assertEquals(teacherAlpha.hashCode(), duplicateTeacher.hashCode(),
                "HashCodes should be identical for teachers with the same ID");
    }

    @Test
    public void shouldHaveDifferentHashCodesForDifferentIds() {
        // Assert: Verify that hashCodes differ for different IDs
        assertNotEquals(teacherAlpha.hashCode(), teacherBeta.hashCode(),
                "HashCodes should differ for teachers with different IDs");
    }

    @Test
    public void shouldBeEqualWhenIdsMatch() {
        // Arrange: Create another Teacher object with the same ID as teacherAlpha
        Teacher duplicateTeacher = new Teacher(101L, "Johnson", "Alice", teacherAlpha.getCreatedAt(), teacherAlpha.getUpdatedAt());

        // Assert: Verify that the objects are equal
        assertEquals(teacherAlpha, duplicateTeacher, "Teachers with the same ID should be considered equal");
    }

    @Test
    public void shouldNotBeEqualWhenIdsDiffer() {
        // Assert: Verify that teacherAlpha and teacherBeta are not equal due to different IDs
        assertNotEquals(teacherAlpha, teacherBeta, "Teachers with different IDs should not be equal");
    }

    @Test
    public void shouldNotEqualNullOrDifferentClass() {
        // Assert: Verify that teacherAlpha is not equal to null or a different object type
        assertNotEquals(null, teacherAlpha, "A teacher should not be equal to null");
        assertNotEquals(teacherAlpha, new Object(), "A teacher should not be equal to an object of a different type");
    }

    @Test
    public void shouldUpdateCreatedAtCorrectly() {
        // Arrange: Set a new createdAt timestamp
        LocalDateTime newTimestamp = LocalDateTime.now().minusDays(5);

        // Act: Update the createdAt value
        teacherAlpha.setCreatedAt(newTimestamp);

        // Assert: Verify that the createdAt value is updated
        assertEquals(newTimestamp, teacherAlpha.getCreatedAt(),
                "The createdAt timestamp should be updated to the new value");
    }

    @Test
    public void shouldUpdateUpdatedAtCorrectly() {
        // Arrange: Set a new updatedAt timestamp
        LocalDateTime newTimestamp = LocalDateTime.now().minusDays(3);

        // Act: Update the updatedAt value
        teacherAlpha.setUpdatedAt(newTimestamp);

        // Assert: Verify that the updatedAt value is updated
        assertEquals(newTimestamp, teacherAlpha.getUpdatedAt(),
                "The updatedAt timestamp should be updated to the new value");
    }
}
