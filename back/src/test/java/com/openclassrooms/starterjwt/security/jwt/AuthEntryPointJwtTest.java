package com.openclassrooms.starterjwt.security.jwt;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.ArgumentCaptor;

class AuthEntryPointJwtTest {

    @InjectMocks
    private AuthEntryPointJwt authEntryPointJwt;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @Mock
    private ServletOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUnauthorizedResponse_WhenCommenceIsCalled() throws IOException, ServletException {
        // Arrange: Prepare necessary objects for the test
        when(authException.getMessage()).thenReturn("Access Denied");
        when(request.getServletPath()).thenReturn("/api/secure-endpoint");
        when(response.getOutputStream()).thenReturn(outputStream);

        // Capture JSON data written to the response
        ArgumentCaptor<byte[]> responseCaptor = ArgumentCaptor.forClass(byte[].class);

        // Act: Call the commence() method to simulate exception handling
        authEntryPointJwt.commence(request, response, authException);

        // Assert: Verify response interactions
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Capture the response body and verify its content
        verify(outputStream).write(responseCaptor.capture(), eq(0), anyInt());

        byte[] capturedResponseBytes = responseCaptor.getValue();
        String capturedResponseBody = new String(capturedResponseBytes);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> actualResponseBody = objectMapper.readValue(capturedResponseBody, Map.class);

        // Expected response
        Map<String, Object> expectedResponseBody = Map.of(
                "status", HttpServletResponse.SC_UNAUTHORIZED,
                "error", "Unauthorized",
                "message", "Access Denied",
                "path", "/api/secure-endpoint");

        // Assert: Compare the actual response with the expected response
        assertEquals(expectedResponseBody, actualResponseBody,
                "The response body does not match the expected response");
    }
}
