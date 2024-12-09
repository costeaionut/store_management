package com.ing.demo.store_management.controller.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticateRequestDTO {

    @NotNull
    @Email(regexp = ".+[@].+[\\.].+", message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull
    @NotBlank(message = "Password is empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
