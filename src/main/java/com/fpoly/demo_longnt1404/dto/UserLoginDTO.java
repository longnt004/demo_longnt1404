package com.fpoly.demo_longnt1404.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "Username is required") // Validate username is not blank
    private String username;


    @NotBlank(message = "Password is required") // Validate password is not blank
    private String password;
}
