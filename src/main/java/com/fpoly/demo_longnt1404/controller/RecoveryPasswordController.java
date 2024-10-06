package com.fpoly.demo_longnt1404.controller;

import com.fpoly.demo_longnt1404.dto.UpdatePasswordDTO;
import com.fpoly.demo_longnt1404.model.User;
import com.fpoly.demo_longnt1404.service.impl.EmailService;
import com.fpoly.demo_longnt1404.service.OtpService;
import com.fpoly.demo_longnt1404.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class RecoveryPasswordController {

    private final UserService userService;
    private final OtpService otpService;
    private final EmailService emailService;

    public RecoveryPasswordController(UserService userService, OtpService otpService, EmailService emailService) {
        this.userService = userService;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @PostMapping("/password-recovery")
    public ResponseEntity<Map<String, String>> recoverPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Validate email user
        User user = userService.findByEmail(email);

        // If the account does not exist return an error
        if (user == null) {
            throw new IllegalArgumentException("Email does not exist");
        }else {
            // If the account exists, generate OTP code and send it to the user's email
            String otp = otpService.generatorOTP(user);
            System.out.println("OTP: " + otp);
                emailService.sendEmail(user.getEmail(), "Recovery Password", "Your OTP code is: " + otp);
                return ResponseEntity.ok(Map.of("message", "OTP code has been sent to your email"));

        }
    }

    @PostMapping("/password/change/{username}")
    public ResponseEntity<Map<String, String>> changePassword(@PathVariable String username ,@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        User user = userService.findByUsername(username);

        String newPassword = updatePasswordDTO.getNewPassword();
        String otp = updatePasswordDTO.getOtp();

        if (otpService.FindOtpByUser(user).equals(otp)) {
            userService.updatePassword(user, newPassword);
            return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
        } else {
            throw new IllegalArgumentException("OTP code is invalid");
        }
    }

}
