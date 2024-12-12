package com.ing.demo.store_management.controller;

import com.ing.demo.store_management.controller.dto.product.ProductRequest;
import com.ing.demo.store_management.controller.dto.product.properties.ClothingProperties;
import com.ing.demo.store_management.controller.dto.product.properties.ElectronicProperties;
import com.ing.demo.store_management.mappers.product.ProductMapper;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.service.InputSanitizer;
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

    private final InputSanitizer sanitizer;
    private final ProductService productService;

    @Autowired
    public ProductController(InputSanitizer sanitizer, ProductService productService) {
        this.sanitizer = sanitizer;
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
    public ResponseEntity<Product> createProduct(@Validated @RequestBody ProductRequest createDto) {
        LOGGER.debug("Processing createProduct request for product: {}.", createDto);

        createDto = sanitizerProductRequest(createDto);

        Product productToCreate = convertDtoToProduct(createDto);
        Product createdProduct = productService.createProduct(productToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Product> updateProduct(@Validated @RequestBody ProductRequest updateDto, @PathVariable int id) {
        LOGGER.debug("Processing updateProduct request for id {} and product new details: {}.", id, updateDto);

        updateDto = sanitizerProductRequest(updateDto);

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

    private Product convertDtoToProduct(ProductRequest dto) {
        ProductMapper<?> mapper = productService.getMapperFromCategory(dto.getCategory());
        return mapper.mapFromDTO(dto);
    }

    private ProductRequest sanitizerProductRequest(ProductRequest request) {
        ProductRequest sanitized = new ProductRequest();

        sanitized.setName(sanitizer.sanitizeInput(request.getName()));
        sanitized.setDescription(sanitizer.sanitizeInput(request.getDescription()));

        switch (request.getCategory()) {
            case ELECTRONICS -> {
                ElectronicProperties properties = request.getElectronicProperties();

                String sanitizedBrand = sanitizer.sanitizeInput(properties.getBrand());
                String sanitizedPowerRequirement = sanitizer.sanitizeInput(properties.getPowerRequirement());

                ElectronicProperties sanitizedProperties =
                        new ElectronicProperties(sanitizedBrand, properties.getWarrantyPeriod(), sanitizedPowerRequirement);
                sanitized.setElectronicProperties(sanitizedProperties);
            }
            case CLOTHING -> {
                ClothingProperties properties = request.getClothingProperties();
                String sanitizedColor = sanitizer.sanitizeInput(properties.getColor());
                String sanitizedMaterial = sanitizer.sanitizeInput(properties.getMaterial());

                ClothingProperties sanitizedProperties =
                        new ClothingProperties(properties.getSize(), sanitizedMaterial, sanitizedColor);
                sanitized.setClothingProperties(sanitizedProperties);
            }
            case GROCERY -> sanitized.setGroceryProperties(request.getGroceryProperties());
            default -> throw new IllegalArgumentException("Category must be ELECTRONICS, GROCERY, CLOTHING");
        }

        LOGGER.debug("Sanitized CreateRequest: {}", sanitized);
        return sanitized;
    }

}
