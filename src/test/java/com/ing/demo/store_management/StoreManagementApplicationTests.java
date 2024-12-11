package com.ing.demo.store_management;

import com.ing.demo.store_management.controller.AuthenticationController;
import com.ing.demo.store_management.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class StoreManagementApplicationTests {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private ProductController productController;

    @Test
    void contextLoads() {
        assertNotNull(authenticationController);
        assertNotNull(productController);
    }

}
