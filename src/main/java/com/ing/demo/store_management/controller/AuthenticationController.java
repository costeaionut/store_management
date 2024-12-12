package com.ing.demo.store_management.controller;

import com.ing.demo.store_management.controller.dto.authentication.AuthenticateRequestDTO;
import com.ing.demo.store_management.controller.dto.authentication.AuthenticateResponseDTO;
import com.ing.demo.store_management.controller.dto.authentication.RegisterRequestDTO;
import com.ing.demo.store_management.mappers.authentication.UserMapper;
import com.ing.demo.store_management.service.InputSanitizer;
import com.ing.demo.store_management.service.JWTService;
import com.ing.demo.store_management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final JWTService jwtService;
    private final UserService userService;
    private final InputSanitizer sanitizer;

    @Autowired
    public AuthenticationController(JWTService jwtService, UserService userService, InputSanitizer sanitizer) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.sanitizer = sanitizer;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequestDTO registerDto) {
        LOGGER.debug("Processing register user request.");

        // 1. Sanitize incoming dto
        registerDto = sanitizeRegisterRequestDTO(registerDto);

        // 2. Register new user
        userService.registerUser(UserMapper.toModel(registerDto));

        // 3. Prepare response
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponseDTO> loginUser(@Validated @RequestBody AuthenticateRequestDTO authenticationDto) {
        LOGGER.debug("Processing login user request.");

        // 1. Sanitize incoming dto
        authenticationDto = sanitizeAuthenticationRequestDTO(authenticationDto);

        // 2. Verify user credentials
        Authentication authentication = userService.verifyUser(authenticationDto.getEmail(), authenticationDto.getPassword());

        // 3. Generate JWT token
        String token = jwtService.generateJWTToken((User) authentication.getPrincipal());

        // 4. Prepare response
        return ResponseEntity.ok().body(new AuthenticateResponseDTO(token));
    }

    private RegisterRequestDTO sanitizeRegisterRequestDTO(RegisterRequestDTO rawDTO) {
        return new RegisterRequestDTO(
                sanitize(rawDTO.getName()),
                sanitize(rawDTO.getSurname()),
                sanitize(rawDTO.getEmail()),
                sanitize(rawDTO.getPassword()),
                rawDTO.getRole()
        );
    }

    private AuthenticateRequestDTO sanitizeAuthenticationRequestDTO(AuthenticateRequestDTO rawDTO) {
        return new AuthenticateRequestDTO(
                sanitize(rawDTO.getEmail()),
                sanitize(rawDTO.getPassword())
        );
    }

    private String sanitize(String rawInput) {
        return sanitizer.sanitizeInput(rawInput);
    }

}