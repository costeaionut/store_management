package com.ing.demo.store_management.service;

import com.ing.demo.store_management.mappers.product.ProductMapper;
import com.ing.demo.store_management.model.product.base.Category;
import com.ing.demo.store_management.model.product.base.Product;

import java.util.List;

public interface ProductService {

    List<Product> retrieveAllProducts();

    Product retrieveProductById(int id);

    Product createProduct(Product newProduct);

    Product updateProduct(int id, Product updatedProduct);

    void deleteProduct(int id);

    ProductMapper<?> getMapperFromCategory(Category category);
}
