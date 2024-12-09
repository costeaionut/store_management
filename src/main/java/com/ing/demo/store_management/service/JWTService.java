package com.ing.demo.store_management.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.User;

import java.util.function.Function;

public interface JWTService {
    String ROLE_CLAIM = "role";

    String generateJWTToken(User principal);

    Claims parseTokenClaims(String token);

    <T> T parseTokenClaim(String token, Function<Claims, T> parser);
}
