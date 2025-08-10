package com.fitnessAI.userService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message="Password is too short! It should contain at leaast 5 characters")
    private String password;
    private String keycloackId;
    private String firstName;
    private String lastName;
}
