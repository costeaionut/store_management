package com.ing.demo.store_management.controller;

import com.ing.demo.store_management.controller.dto.product.CreateProductRequest;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.model.product.concrete.Electronic;
import com.ing.demo.store_management.model.product.concrete.Grocery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(List.of(new Electronic(), new Grocery()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSpecificProduct(@PathVariable int id) {
        return ResponseEntity.ok().body(new Electronic());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Product> createProduct(@Validated @RequestBody CreateProductRequest createDto) {
        return ResponseEntity.ok().body(new Product());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<?> updateProduct(@Validated @RequestBody CreateProductRequest updateDto, @PathVariable int id) {
        return ResponseEntity.ok().body("Updated");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        return ResponseEntity.ok().body("Deleted");
    }

}
