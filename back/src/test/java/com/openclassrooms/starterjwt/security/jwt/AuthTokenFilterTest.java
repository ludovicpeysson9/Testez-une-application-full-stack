package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthTokenFilterTest {

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test and clear the security context
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateUser_WhenJwtIsValid() throws ServletException, IOException {
        // Arrange: Set up a valid JWT token scenario
        String validJwt = "sample-valid-jwt";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validJwt);
        when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(validJwt)).thenReturn("alphaUser");
        when(userDetailsService.loadUserByUsername("alphaUser")).thenReturn(userDetails);

        // Act: Simulate the filter with a valid JWT
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert: Verify interactions and ensure authentication is set
        verify(jwtUtils, times(1)).validateJwtToken(validJwt);
        verify(userDetailsService, times(1)).loadUserByUsername("alphaUser");
        verify(filterChain, times(1)).doFilter(request, response);

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        assertNotNull(authentication, "Authentication should not be null for a valid JWT");
        assertEquals(userDetails, authentication.getPrincipal(),
                "The principal in the authentication should match the UserDetails loaded");
    }

    @Test
    void shouldNotAuthenticateUser_WhenJwtIsInvalid() throws ServletException, IOException {
        // Arrange: Set up an invalid JWT token scenario
        String invalidJwt = "invalid-jwt-sample";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidJwt);
        when(jwtUtils.validateJwtToken(invalidJwt)).thenReturn(false);

        // Act: Simulate the filter with an invalid JWT
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert: Verify that no authentication is set and the filter chain continues
        verify(jwtUtils, times(1)).validateJwtToken(invalidJwt);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should remain null for an invalid JWT");
    }

    @Test
    void shouldSkipAuthentication_WhenNoJwtIsProvided() throws ServletException, IOException {
        // Arrange: Simulate a request without an Authorization header
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act: Simulate the filter without a JWT token
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert: Verify that no authentication is set and the filter chain continues
        verify(jwtUtils, never()).validateJwtToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should remain null when no JWT is provided");
    }

    @Test
    void shouldHandleMalformedJwtToken() throws ServletException, IOException {
        // Arrange: Provide a malformed JWT token
        String malformedJwt = "malformed-jwt-sample";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + malformedJwt);
        when(jwtUtils.validateJwtToken(malformedJwt)).thenThrow(new IllegalArgumentException("Malformed token"));

        // Act: Simulate the filter with a malformed JWT
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert: Verify that the filter handles the exception gracefully
        verify(jwtUtils, times(1)).validateJwtToken(malformedJwt);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should remain null for a malformed JWT");
    }
}
