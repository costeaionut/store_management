package com.ing.demo.store_management.controller;

import com.ing.demo.store_management.model.authentication.Role;
import com.ing.demo.store_management.model.authentication.StoreUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private StoreUser validUser;

    @BeforeEach
    public void setUp() {
        validUser = new StoreUser("John", "Doe", "john.doe@example.com", "password123", Role.USER);
    }

    @Test
    public void testRegistration_Successful() {
        ResponseEntity<String> response =
                restTemplate.postForEntity("http://localhost:" + port + "/api/auth/register", validUser, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    public void testRegistration_UserDuplicated() {
        restTemplate.postForEntity("http://localhost:" + port + "/api/auth/register", validUser, String.class);
        ResponseEntity<String> response =
                restTemplate.postForEntity("http://localhost:" + port + "/api/auth/register", validUser, String.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email is already taken.", response.getBody());
    }

    @Test
    public void testRegistration_InvalidUser() {
        ResponseEntity<String> response =
                restTemplate.postForEntity("http://localhost:" + port + "/api/auth/register", null, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("An unexpected error occurred."));
        assertTrue(response.getBody().contains("Required request body is missing"));
    }

}
