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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve all products in the repository
     *
     * @return a list of all the products
     */
    @Override
    public List<Product> retrieveAllProducts() {
        LOGGER.debug("Retrieve all products from database.");
        return repository.findAll();
    }

    /**
     * Retrieve a product based on the id
     *
     * @param id product id
     * @return the product with the specified id
     */
    @Override
    public Product retrieveProductById(int id) {
        LOGGER.debug("Retrieve product by id form database for id: {}.", id);

        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            LOGGER.error("No product in database for id: {}.", id);
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return product.get();
    }

    /**
     * Add a new product in the database
     *
     * @param newProduct add the product
     * @return the created product
     */
    @Override
    public Product createProduct(@Validated Product newProduct) {
        LOGGER.debug("Create new product with details: {}.", newProduct);

        if (newProduct == null) {
            LOGGER.error("Missing new product details.");
            throw new IllegalArgumentException("Product can't be null");
        }
        return repository.save(newProduct);
    }

    /**
     * Update a product based on the id and the new values
     *
     * @param id             product id
     * @param updatedProduct product new info
     * @return the updated product
     */
    @Override
    public Product updateProduct(int id, @Validated Product updatedProduct) {
        if (updatedProduct == null) {
            LOGGER.error("Missing updated product details for product with id: {}.", id);
            throw new IllegalArgumentException("Updated product can't be null");
        }

        Product existingProduct = retrieveProductById(id);
        ProductMapper<?> mapper = getMapperFromCategory(updatedProduct.getCategory());
        mapper.updateProductFields(existingProduct, updatedProduct);

        return existingProduct;
    }

    /**
     * Delete a product based on id
     *
     * @param id product id
     */
    @Override
    public void deleteProduct(int id) {
        LOGGER.debug("Deleting entry for product with id: {}.", id);
        repository.deleteById(id);
    }

    /**
     * Retrieves the appropriate mapper based on the category of product
     *
     * @param category product category
     * @return the product mapper
     */
    @Override
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
