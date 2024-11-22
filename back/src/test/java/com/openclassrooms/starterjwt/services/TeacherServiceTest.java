package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        // Initialize mocks and prepare data for tests
        MockitoAnnotations.openMocks(this);

        // Create a teacher instance
        teacher = new Teacher()
                .setId(100L)
                .setFirstName("Alice")
                .setLastName("Johnson");
    }

    @Test
    void testFindAllTeachers() {
        // Arrange: Mock repository to return multiple teachers
        Teacher teacher2 = new Teacher().setId(200L).setFirstName("Bob").setLastName("Smith");
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher, teacher2));

        // Act: Call the method to retrieve all teachers
        List<Teacher> teachers = teacherService.findAll();

        // Assert: Verify the results
        assertThat(teachers).hasSize(2);
        assertThat(teachers).containsExactlyInAnyOrder(teacher, teacher2);

        // Ensure the repository's findAll method was called once
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void testFindById_TeacherExists() {
        // Arrange: Mock repository to return an existing teacher
        Long teacherId = 100L;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        // Act: Call the method to find a teacher by ID
        Teacher foundTeacher = teacherService.findById(teacherId);

        // Assert: Verify the returned teacher matches the expected one
        assertThat(foundTeacher).isNotNull();
        assertThat(foundTeacher.getId()).isEqualTo(teacherId);
        assertThat(foundTeacher.getFirstName()).isEqualTo("Alice");

        // Ensure the repository's findById method was called once with the correct ID
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    void testFindById_TeacherNotFound() {
        // Arrange: Mock repository to simulate a non-existent teacher
        Long teacherId = 100L;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        // Act: Call the method to find a teacher by ID
        Teacher foundTeacher = teacherService.findById(teacherId);

        // Assert: Verify the method returns null when the teacher does not exist
        assertThat(foundTeacher).isNull();

        // Ensure the repository's findById method was called once with the correct ID
        verify(teacherRepository, times(1)).findById(teacherId);
    }
}
