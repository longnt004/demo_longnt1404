package com.fpoly.demo_longnt1404.controller;

import com.fpoly.demo_longnt1404.dto.UserDTO;
import com.fpoly.demo_longnt1404.mapper.UserMapper;
import com.fpoly.demo_longnt1404.model.User;
import com.fpoly.demo_longnt1404.service.impl.EmailService;
import com.fpoly.demo_longnt1404.service.OtpService;
import com.fpoly.demo_longnt1404.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class SignUpController {

    private final UserService userService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final OtpService otpService;
    private final EmailService emailService;

    public SignUpController(UserService userService, OtpService otpService, EmailService emailService) {
        this.userService = userService;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDto) {
        try {
            // Change UserDTO to User
            User user = userMapper.userDTOToUser(userDto);

            // Set User enabled to false (User must verify email to enable account)
            user.setEnabled(false);

            // Save User
            userService.save(user);

            // Generate OTP code
            String otp = otpService.generatorOTP(user);
            System.out.println("OTP: " + otp);

            // Send OTP code to user's email
            emailService.sendEmail(user.getEmail(), "Verify Account: ", "Your OTP code: " + otp);

            // Return the saved UserDTO to the client
            return new ResponseEntity<>("OTP code has been sent to your email", HttpStatus.CREATED);
        } catch (Exception e) {
            // If an error occurs, return an error message to the client
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/verification/{username}")
    public ResponseEntity<UserDTO> verifyUser(@PathVariable String username, @RequestBody Map<String, String> request) throws Exception {
        String otp = request.get("otp");
        try {
            // Find User by username
            User user = userService.findByUsername(username);

            // If the account does not exist return an error
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Validate OTP code
            boolean isValid = otpService.validateOTP(user, otp);

            // If the OTP code is valid, enable the account
            if (isValid) {
                user.setEnabled(true);
                userService.update(user);
                return ResponseEntity.ok(userMapper.userToUserDTO(user));
            } else {
                throw new Exception("Your OTP is invalid or expired");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
