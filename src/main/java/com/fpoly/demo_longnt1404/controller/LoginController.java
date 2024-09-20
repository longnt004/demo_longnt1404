package com.fpoly.demo_longnt1404.controller;

import com.fpoly.demo_longnt1404.dto.UserLoginDTO;
import com.fpoly.demo_longnt1404.utlis.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LoginController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/sessions")
    public ResponseEntity<Map<String, String>> createSession(@Valid @RequestBody UserLoginDTO request) {
        try {
            // Validate the user's information
            Authentication authentication = authenticationManager.authenticate(
                    // Create UsernamePasswordAuthenticationToken object
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            // If the information is valid, create a token
            String jwt = jwtUtil.generateToken(authentication.getName());
            // Return the token to the client
            return ResponseEntity.ok(Map.of("token", jwt));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
