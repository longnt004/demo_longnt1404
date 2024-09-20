package com.fpoly.demo_longnt1404.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    @NotBlank(message = "OTP is required.")
    @Size(min = 4, max = 4, message = "OTP must be 4 digits.")
    private String otp;

    @NotBlank(message = "Password is required") // Validate password is not blank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must contain at least 8 characters, including uppercase, lowercase letters and numbers")
    private String newPassword;
}
