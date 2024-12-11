package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.exception.product.ProductNotFoundException;
import com.ing.demo.store_management.mappers.product.ClothingProductMapper;
import com.ing.demo.store_management.mappers.product.ElectronicProductMapper;
import com.ing.demo.store_management.mappers.product.GroceryProductMapper;
import com.ing.demo.store_management.mappers.product.ProductMapper;
import com.ing.demo.store_management.model.product.base.Category;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.repository.ProductRepository;
import com.ing.demo.store_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> retrieveAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product retrieveProductById(int id) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return product.get();
    }

    @Override
    public Product createProduct(@Validated Product newProduct) {
        if (newProduct == null) {
            throw new IllegalArgumentException("Product can't be null");
        }
        return repository.save(newProduct);
    }

    @Override
    public Product updateProduct(int id, @Validated Product updatedProduct) {
        if (updatedProduct == null) {
            throw new IllegalArgumentException("Updated product can't be null");
        }

        Product existingProduct = retrieveProductById(id);
        ProductMapper<?> mapper = getMapperFromCategory(updatedProduct.getCategory());
        mapper.updateProductFields(existingProduct, updatedProduct);

        return existingProduct;
    }

    @Override
    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    public ProductMapper<?> getMapperFromCategory(Category category) {
        switch (category) {
            case GROCERY -> {
                return new GroceryProductMapper();
            }
            case ELECTRONICS -> {
                return new ElectronicProductMapper();
            }
            case CLOTHING -> {
                return new ClothingProductMapper();
            }
            default -> throw new IllegalArgumentException("Category must be ELECTRONICS, GROCERY, CLOTHING");
        }
    }
}
