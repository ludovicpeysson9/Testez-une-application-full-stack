package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and inject necessary values into jwtUtils
        MockitoAnnotations.openMocks(this);

        // Configure jwtUtils with a secret key and expiration time
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "customSecretKey");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 7200000); // 2 hours
    }

    @Test
    void shouldGenerateJwtToken() {
        // Arrange: Prepare a mock user for testing
        UserDetailsImpl userDetails = new UserDetailsImpl(101L, "alphaUser", "Alice", "Smith", false, "pass123");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act: Generate a JWT token
        String token = jwtUtils.generateJwtToken(authentication);

        // Assert: Verify that the token is not null and appears to be a JWT
        assertNotNull(token, "Generated JWT token should not be null");
        assertTrue(token.startsWith("eyJ"), "Generated token should start with 'eyJ', indicating a valid JWT format");
    }

    @Test
    void shouldExtractUsernameFromJwtToken() {
        // Arrange: Generate a valid JWT
        UserDetailsImpl userDetails = new UserDetailsImpl(102L, "betaUser", "Bob", "Johnson", true, "pass456");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = jwtUtils.generateJwtToken(authentication);

        // Act: Extract the username from the token
        String username = jwtUtils.getUserNameFromJwtToken(token);

        // Assert: Verify that the extracted username matches the expected value
        assertEquals("betaUser", username, "The username extracted from the token should match the expected value");
    }

    @Test
    void shouldValidateJwtToken_WhenTokenIsValid() {
        // Arrange: Generate a valid JWT
        UserDetailsImpl userDetails = new UserDetailsImpl(103L, "gammaUser", "Charlie", "Brown", false, "pass789");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = jwtUtils.generateJwtToken(authentication);

        // Act: Validate the token
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Assert: Verify that the token is valid
        assertTrue(isValid, "A valid JWT token should pass validation");
    }

    @Test
    void shouldInvalidateJwtToken_WhenTokenIsInvalid() {
        // Arrange: Define an invalid token
        String invalidToken = "invalidJWT";

        // Act: Validate the invalid token
        boolean isValid = jwtUtils.validateJwtToken(invalidToken);

        // Assert: Verify that the token is considered invalid
        assertFalse(isValid, "An invalid JWT token should not pass validation");
    }

    @Test
    void shouldInvalidateJwtToken_WhenMalformed() {
        // Arrange: Define a malformed token
        String malformedToken = "malformedJWT";

        // Act: Validate the malformed token
        boolean isValid = jwtUtils.validateJwtToken(malformedToken);

        // Assert: Verify that the malformed token is considered invalid
        assertFalse(isValid, "A malformed JWT token should not pass validation");
    }

    @Test
    void shouldInvalidateJwtToken_WhenEmpty() {
        // Arrange: Define an empty token
        String emptyToken = "";

        // Act: Validate the empty token
        boolean isValid = jwtUtils.validateJwtToken(emptyToken);

        // Assert: Verify that the empty token is considered invalid
        assertFalse(isValid, "An empty JWT token should not pass validation");
    }

    @Test
    void shouldInvalidateJwtToken_WhenExpired() {
        // Arrange: Configure jwtUtils with a negative expiration to simulate an expired token
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", -5000); // Token expired
        UserDetailsImpl userDetails = new UserDetailsImpl(104L, "deltaUser", "Diana", "Prince", true, "passSecret");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String expiredToken = jwtUtils.generateJwtToken(authentication);

        // Act: Validate the expired token
        boolean isValid = jwtUtils.validateJwtToken(expiredToken);

        // Assert: Verify that the expired token is considered invalid
        assertFalse(isValid, "An expired JWT token should not pass validation");
    }
}
