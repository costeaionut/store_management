package com.ing.demo.store_management.controller;

import com.ing.demo.store_management.controller.dto.product.ProductRequestDTO;
import com.ing.demo.store_management.mappers.product.ProductMapper;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        LOGGER.debug("Processing getAllProducts request.");
        return ResponseEntity.ok().body(productService.retrieveAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSpecificProduct(@PathVariable int id) {
        LOGGER.debug("Processing getSpecificProduct request for id: {}.", id);

        Product product = productService.retrieveProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Product> createProduct(@Validated @RequestBody ProductRequestDTO createDto) {
        LOGGER.debug("Processing createProduct request for product: {}.", createDto);

        Product productToCreate = convertDtoToProduct(createDto);
        Product createdProduct = productService.createProduct(productToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Product> updateProduct(@Validated @RequestBody ProductRequestDTO updateDto, @PathVariable int id) {
        LOGGER.debug("Processing updateProduct request for id {} and product new details: {}.", id, updateDto);

        Product productToUpdate = convertDtoToProduct(updateDto);
        Product updatedProduct = productService.updateProduct(id, productToUpdate);

        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        LOGGER.debug("Processing deleteProduct request for id: {}.", id);

        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    private Product convertDtoToProduct(ProductRequestDTO dto) {
        ProductMapper<?> mapper = productService.getMapperFromCategory(dto.getCategory());
        return mapper.mapFromDTO(dto);
    }
}
