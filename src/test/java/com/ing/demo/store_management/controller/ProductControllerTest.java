package com.ing.demo.store_management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.demo.store_management.controller.dto.product.ProductRequest;
import com.ing.demo.store_management.controller.dto.product.properties.GroceryProperties;
import com.ing.demo.store_management.mappers.product.GroceryProductMapper;
import com.ing.demo.store_management.model.authentication.Role;
import com.ing.demo.store_management.model.authentication.StoreUser;
import com.ing.demo.store_management.model.product.base.Category;
import com.ing.demo.store_management.model.product.concrete.Grocery;
import com.ing.demo.store_management.repository.ProductRepository;
import com.ing.demo.store_management.repository.UserRepository;
import com.ing.demo.store_management.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestPropertySource;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private Grocery grocery;
    private ProductRequest productRequest;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
        userRepository.deleteAll();

        productRequest = prepareProductRequest();
        grocery = prepareGroceryProduct(productRequest);
        userRepository.save(
                new StoreUser("Test", "User", "admin@gmail.com", "testPass", Role.ADMIN)
        );
    }

    @Test
    public void testRoleProtectedEndpoints_AuthorizationFail() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getUserToken());
        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);

        // Try to access the create request with USER token
        assertEquals(
                HttpStatus.FORBIDDEN,
                restTemplate.postForEntity("http://localhost:" + port + "/api/product/", requestEntity, String.class)
                        .getStatusCode()
        );

        // Try to access the update request with USER token
        assertEquals(
                HttpStatus.FORBIDDEN,
                restTemplate.exchange("http://localhost:" + port + "/api/product/{id}", HttpMethod.PUT,
                        requestEntity, String.class, 1
                ).getStatusCode()
        );

        // Try to access the delete request with USER token
        assertEquals(
                HttpStatus.FORBIDDEN,
                restTemplate.exchange("http://localhost:" + port + "/api/product/{id}", HttpMethod.DELETE,
                        new HttpEntity<Void>(headers), String.class, 1
                ).getStatusCode()
        );
    }

    @Test
    public void testRoleProtectedEndpoints_SuccessfulCall() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAdminToken());
        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);

        // Call create endpoint
        ResponseEntity<String> createResponse =
                restTemplate.postForEntity("http://localhost:" + port + "/api/product/", requestEntity, String.class);
        Grocery resultCreatedGrocery = objectMapper.readValue(createResponse.getBody(), Grocery.class);

        // Assert create endpoint
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertGroceryProduct(grocery, resultCreatedGrocery);
        assertEquals(1, productRepository.count());

        // Call update endpoint
        grocery.setName("UpdatedName");
        productRequest.setName("UpdatedName");
        int productId = resultCreatedGrocery.getId();
        ResponseEntity<String> updatedResponse =
                restTemplate.exchange("http://localhost:" + port + "/api/product/{id}", HttpMethod.PUT,
                        requestEntity, String.class, productId);
        Grocery updatedGrocery = objectMapper.readValue(updatedResponse.getBody(), Grocery.class);

        assertEquals(HttpStatus.OK, updatedResponse.getStatusCode());
        assertGroceryProduct(grocery, updatedGrocery);
        assertEquals(1, productRepository.count());


        // Try to access the delete request with USER token
        assertEquals(
                HttpStatus.OK,
                restTemplate.exchange("http://localhost:" + port + "/api/product/{id}", HttpMethod.DELETE,
                        new HttpEntity<Void>(headers), String.class, productId
                ).getStatusCode()
        );
        assertEquals(0, productRepository.count());
    }

    private Grocery prepareGroceryProduct(ProductRequest productRequest) {
        return new GroceryProductMapper().mapFromDTO(productRequest);
    }

    private ProductRequest prepareProductRequest() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("TestGrocery");
        productRequest.setDescription("TestGrocery Description");
        productRequest.setCategory(Category.GROCERY);
        productRequest.setPrice(10.00);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 6);
        GroceryProperties groceryProperties = new GroceryProperties(calendar.getTime(), 9.8, true);

        productRequest.setGroceryProperties(groceryProperties);

        return productRequest;
    }

    private String getAdminToken() {
        return jwtService.generateJWTToken(
                new User("admin@gmail.com",
                        "",
                        Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))
                )
        );
    }

    private String getUserToken() {
        return jwtService.generateJWTToken(
                new User("user@gmail.com",
                        "",
                        Collections.singletonList(new SimpleGrantedAuthority("USER"))
                )
        );
    }

    private void assertGroceryProduct(Grocery expected, Grocery actual) {
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getExpiryDate(), actual.getExpiryDate());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.isPerishable(), actual.isPerishable());
    }

}
