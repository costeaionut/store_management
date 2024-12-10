package com.ing.demo.store_management.model.product.concrete;

import com.ing.demo.store_management.model.product.base.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Grocery extends Product {

    @Column(nullable = false)
    @NotEmpty(message = "Expiry date cannot be empty")
    @Future(message = "Expiry date must be a future date")
    private String expiryDate;

    @Column(nullable = false)
    @Positive(message = "Weight must be positive")
    private double weight; // in kg

    @Column(nullable = false)
    private boolean isPerishable;
}
