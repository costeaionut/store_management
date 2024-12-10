package com.ing.demo.store_management.model.product.concrete;

import com.ing.demo.store_management.model.product.base.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Electronic extends Product {

    @Column(nullable = false)
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;

    @Column(nullable = false)
    @Positive(message = "Warranty period must be positive")
    private int warrantyPeriod; // in months

    @Column(nullable = false, length = 50)
    @NotEmpty(message = "Power requirement cannot be empty")
    private String powerRequirement;
}


