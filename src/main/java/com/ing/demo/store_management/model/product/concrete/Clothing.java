package com.ing.demo.store_management.model.product.concrete;

import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.model.product.base.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Clothing extends Product {

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Size cannot be empty")
    private Size size;

    @Column(nullable = false)
    @jakarta.validation.constraints.Size(min = 5, max = 100, message = "Material can have a minimum of 5 maximum of 100 characters")
    private String material;

    @Column(nullable = false)
    @NotEmpty(message = "Color cannot be empty")
    @jakarta.validation.constraints.Size(min = 3, max = 50, message = "Color can have a minimum of 3 maximum of 50 characters")
    private String color;
}
