package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SessionMapperTest {

    @Autowired
    private SessionMapper sessionMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private TeacherService teacherService;

    private Teacher teacher;
    private User user1;
    private User user2;

    @BeforeEach
    void setupTestEnvironment() {
        // Initialize Teacher object
        teacher = Teacher.builder()
                .id(1001L)
                .firstName("Sarah")
                .lastName("Connor")
                .build();

        // Initialize User objects
        user1 = new User()
                .setId(501L)
                .setFirstName("Michael")
                .setLastName("Smith");

        user2 = new User()
                .setId(502L)
                .setFirstName("Alice")
                .setLastName("Johnson");

        // Mock teacher and user services
        when(teacherService.findById(1001L)).thenReturn(teacher);
        when(userService.findById(501L)).thenReturn(user1);
        when(userService.findById(502L)).thenReturn(user2);
    }

    @Test
    void shouldMapSessionListToSessionDtoList() {
        // Arrange: Create session objects
        Session sessionOne = Session.builder()
                .id(3001L)
                .name("Art Workshop")
                .teacher(teacher)
                .build();

        Session sessionTwo = Session.builder()
                .id(3002L)
                .name("Music Class")
                .teacher(teacher)
                .build();

        List<Session> sessionList = List.of(sessionOne, sessionTwo);

        // Act: Map Session objects to SessionDto objects
        List<SessionDto> sessionDtoList = sessionMapper.toDto(sessionList);

        // Assert: Validate the mapping results
        assertNotNull(sessionDtoList, "SessionDto list should not be null");
        assertEquals(2, sessionDtoList.size(), "The size of SessionDto list should be 2");
        assertEquals(sessionOne.getId(), sessionDtoList.get(0).getId(), "First session ID should match");
        assertEquals(sessionTwo.getId(), sessionDtoList.get(1).getId(), "Second session ID should match");
    }

    @Test
    void shouldMapSessionDtoListToSessionList() {
        // Arrange: Create SessionDto objects
        SessionDto sessionDtoOne = new SessionDto();
        sessionDtoOne.setId(3001L);
        sessionDtoOne.setName("Art Workshop");
        sessionDtoOne.setTeacher_id(1001L);

        SessionDto sessionDtoTwo = new SessionDto();
        sessionDtoTwo.setId(3002L);
        sessionDtoTwo.setName("Music Class");
        sessionDtoTwo.setTeacher_id(1001L);

        List<SessionDto> sessionDtoList = List.of(sessionDtoOne, sessionDtoTwo);

        // Act: Map SessionDto objects to Session entities
        List<Session> sessionList = sessionMapper.toEntity(sessionDtoList);

        // Assert: Validate the mapping results
        assertNotNull(sessionList, "Session list should not be null");
        assertEquals(2, sessionList.size(), "The size of the Session list should be 2");
        assertEquals(sessionDtoOne.getId(), sessionList.get(0).getId(), "First session ID should match");
        assertEquals(sessionDtoTwo.getId(), sessionList.get(1).getId(), "Second session ID should match");
    }
}
