package com.ing.demo.store_management.security.filter;

import com.ing.demo.store_management.config.SecurityConfigProperties;
import com.ing.demo.store_management.service.JWTService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);

    private static final String TOKEN_START_SEQUENCE = "Bearer ";
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";

    private final JWTService jwtService;
    private final SecurityConfigProperties securityConfigProperties;

    @Autowired
    public JWTFilter(JWTService jwtService, SecurityConfigProperties securityConfigProperties) {
        this.jwtService = jwtService;
        this.securityConfigProperties = securityConfigProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_KEY);

        if (!isValidAuthorizationHeader(authorizationHeader)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing or invalid");
            return;
        }

        try {
            String token = getBearerFromAuthorizationHeader(authorizationHeader);

            String email = jwtService.parseTokenClaim(token, Claims::getSubject);
            String role = jwtService.parseTokenClaim(token, claims -> claims.get(JWTService.ROLE_CLAIM, String.class));

            if (email == null || role == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }

            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            LOGGER.error("Unexpected error during token validation {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "An error occurred during token validation: " + e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return securityConfigProperties
                .getIgnoredPaths().stream()
                .map(path -> path.replace("**", ""))
                .anyMatch(path -> request.getRequestURI().startsWith(path));
    }

    private boolean isValidAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_START_SEQUENCE);
    }

    private String getBearerFromAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader.substring(TOKEN_START_SEQUENCE.length());
    }
}
