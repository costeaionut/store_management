package com.ing.demo.store_management.controller;

import com.ing.demo.store_management.controller.dto.product.CreateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<?> createProduct(@Validated @RequestBody CreateProductRequest createDto) {
        return ResponseEntity.ok().body(createDto);
    }

}
