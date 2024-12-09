package com.ing.demo.store_management.controller;

import com.ing.demo.store_management.controller.dto.authentication.RegisterRequestDTO;
import com.ing.demo.store_management.controller.mappers.UserMapper;
import com.ing.demo.store_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequestDTO registerDto) {
        userService.registerUser(UserMapper.toModel(registerDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
