package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.exception.product.ProductNotFoundException;
import com.ing.demo.store_management.model.product.base.Category;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Mock
    ProductRepository repositoryMock;

    ProductServiceImpl productService;

    Product product;

    @BeforeEach
    public void setup() {
        repositoryMock = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(repositoryMock);
        product = new Product();
        product.setName("Test");
        product.setDescription("Test Description");
        product.setCategory(Category.ELECTRONICS);
        product.setPrice(356.10);
    }

    @Test
    public void testCreateProduct_Successful() {
        assertDoesNotThrow(() -> productService.createProduct(product));
        verify(repositoryMock, times(1)).save(product);
    }

    @Test
    public void testCreateProduct_Exception() {
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(null));
        verify(repositoryMock, times(0)).save(product);
    }

    @Test
    public void testGetProduct_Successful() {
        int productId = 1;
        when(repositoryMock.findById(productId)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.retrieveAllProducts());
        verify(repositoryMock, times(1)).findAll();

        assertDoesNotThrow(() -> productService.retrieveProductById(productId));
        verify(repositoryMock, times(1)).findById(productId);
    }

    @Test
    public void testGetProduct_NoProductId() {
        int productId = 2;
        when(repositoryMock.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.retrieveProductById(productId));
        verify(repositoryMock, times(1)).findById(productId);
    }

    @Test
    public void testUpdateProduct_Successful() {
        int productId = 1;
        when(repositoryMock.findById(productId)).thenReturn(Optional.of(product));

        Product newProduct = new Product();
        newProduct.setName("Test 1");
        newProduct.setDescription("Test Description");
        newProduct.setCategory(Category.ELECTRONICS);
        newProduct.setPrice(356.10);

        assertDoesNotThrow(() -> productService.updateProduct(productId, newProduct));
    }

    @Test
    public void testUpdateProduct_WrongParameters() {
        int productId = 2;
        when(repositoryMock.findById(productId)).thenReturn(Optional.empty());

        Product newProduct = new Product();
        newProduct.setName("Test 1");
        newProduct.setDescription("Test Description");
        newProduct.setCategory(Category.ELECTRONICS);
        newProduct.setPrice(356.10);

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, newProduct));
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productId, null));
    }

    @Test
    public void testDeleteProduct_Successful() {
        int productId = 2;

        assertDoesNotThrow(() -> productService.deleteProduct(productId));
        verify(repositoryMock, times(1)).deleteById(productId);
    }
}
