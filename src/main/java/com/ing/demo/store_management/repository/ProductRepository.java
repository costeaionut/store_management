package com.ing.demo.store_management.repository;

import com.ing.demo.store_management.model.product.base.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
