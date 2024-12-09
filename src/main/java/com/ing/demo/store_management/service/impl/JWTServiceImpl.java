package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    private static final long EXPIRATION_TIME = 86_400_000L; // 24 hours in milliseconds

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String generateJWTToken(User principal) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE_CLAIM, getRoleFromAuthority(principal.getAuthorities()));

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(principal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .and()
                .signWith(getKey())
                .compact();
    }

    @Override
    public Claims parseTokenClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public <T> T parseTokenClaim(String token, Function<Claims, T> parser) {
        final Claims claims = parseTokenClaims(token);
        return parser.apply(claims);
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private String getRoleFromAuthority(Collection<GrantedAuthority> authorities) {
        if (authorities.isEmpty()) {
            throw new IllegalArgumentException("No roles found for the user");
        }

        return authorities.iterator().next().getAuthority();
    }
}
