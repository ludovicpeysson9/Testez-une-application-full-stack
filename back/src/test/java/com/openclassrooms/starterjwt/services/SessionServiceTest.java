package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import com.openclassrooms.starterjwt.models.Session;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testCreateSession() {
        // Arrange
        Session session = new Session(); // Crée un objet Session
        when(sessionRepository.save(session)).thenReturn(session); // Simule le comportement de save

        // Act
        Session result = sessionService.create(session);

        // Assert
        assertEquals(session, result); // Vérifie que le résultat est bien le même que l'objet session
        verify(sessionRepository, times(1)).save(session); // Vérifie que save est bien appelé une fois
    }

    
}
