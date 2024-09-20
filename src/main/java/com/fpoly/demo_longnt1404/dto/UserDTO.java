package com.fpoly.demo_longnt1404.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "Username is required") // Validate username is not blank
    private String username;

    @NotBlank(message = "Password is required") // Validate password is not blank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must contain at least 8 characters, including uppercase, lowercase letters and numbers")
    private String password;

    @NotBlank(message = "Email is required") // Validate email is not blank
    @Email(message = "Email format is incorrect") // Validate email is valid
    private String email;

    @NotBlank(message = "Fullname is required") // Validate fullname is not blank
    private String fullname;
    private Boolean enabled;
    private List<RoleDTO> roles;
}
