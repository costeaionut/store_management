package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.model.authentication.Role;
import com.ing.demo.store_management.service.JWTService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
public class JWTServiceImplTests {

    @Value("${test.jwt.token}")
    private String testToken;

    private User userPrincipal;

    @Autowired
    private JWTServiceImpl jwtService;

    @BeforeEach
    public void setUp() {
        userPrincipal =
                new User("test@example.com", "", Collections.singleton(new SimpleGrantedAuthority(Role.USER.name())));
    }

    @Test
    public void testParseToken() {
        String email = jwtService.parseTokenClaim(testToken, Claims::getSubject);
        String role = jwtService.parseTokenClaim(testToken, claim -> claim.get(JWTService.ROLE_CLAIM, String.class));

        assertEquals(userPrincipal.getUsername(), email);
        assertEquals(Role.USER.name(), role);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateJWTToken(userPrincipal);

        assertEquals(userPrincipal.getUsername(), jwtService.parseTokenClaim(token, Claims::getSubject));
        assertEquals(Role.USER.name(),
                jwtService.parseTokenClaim(testToken, claim -> claim.get(JWTService.ROLE_CLAIM, String.class)));
    }
}
