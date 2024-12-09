package com.ing.demo.store_management.service;

import org.springframework.security.core.userdetails.User;

public interface JWTService {
    String generateJWTToken(User principal);
}
