package com.ing.demo.store_management.controller.dto.product;

import com.ing.demo.store_management.model.product.base.Category;
import com.ing.demo.store_management.model.product.base.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateProductRequest {

    // Base-product specific fields
    @NotEmpty(message = "Name can not be empty")
    private String name;

    @NotEmpty(message = "Description can not be empty")
    private String description;

    @Positive(message = "Price must be positive")
    private double price;

    @NotNull(message = "Category can not be null")
    private Category category;

    // Electronics-specific fields
    private String brand;
    private int warrantyPeriod;
    private String powerRequirement;

    // Clothing-specific fields
    private Size size;
    private String material;
    private String color;

    // Grocery-specific fields
    private String expiryDate;
    private double weight;
    private boolean isPerishable;
}
