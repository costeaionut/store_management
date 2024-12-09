package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.service.JWTService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JWTServiceImpl implements JWTService {

    private static final long EXPIRATION_TIME = 86_400_000L; // 24 hours in milliseconds

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String generateJWTToken(User principal) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", principal.getUsername());
        claims.put("roles", getClaimsFromAuthority(principal.getAuthorities()));

        return Jwts.builder()
                .claims()
                    .add(claims)
                .subject(principal.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .and()
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    private List<String> getClaimsFromAuthority(Collection<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
