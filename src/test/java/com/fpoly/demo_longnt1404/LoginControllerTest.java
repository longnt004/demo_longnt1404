package com.fpoly.demo_longnt1404;

import com.fpoly.demo_longnt1404.controller.LoginController;
import com.fpoly.demo_longnt1404.dto.UserLoginDTO;
import com.fpoly.demo_longnt1404.utlis.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSession_HappyCase() {
        UserLoginDTO request = new UserLoginDTO("validUser", "validPassword");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getName()).thenReturn("validUser");
        when(jwtUtil.generateToken("validUser")).thenReturn("validToken");

        ResponseEntity<Map<String, String>> response = loginController.createSession(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("validToken", response.getBody().get("token"));
    }

    @Test
    void createSession_InvalidUsername() {
        UserLoginDTO request = new UserLoginDTO("invalidUser", "validPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Invalid username"));

        ResponseEntity<Map<String, String>> response = loginController.createSession(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid username", response.getBody().get("message"));
    }

    @Test
    void createSession_InvalidPassword() {
        UserLoginDTO request = new UserLoginDTO("validUser", "invalidPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Invalid password"));

        ResponseEntity<Map<String, String>> response = loginController.createSession(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid password", response.getBody().get("message"));
    }

    @Test
    void createSession_EmptyCredentials() {
        UserLoginDTO request = new UserLoginDTO("", "");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Empty credentials"));

        ResponseEntity<Map<String, String>> response = loginController.createSession(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Empty credentials", response.getBody().get("message"));
    }
}
