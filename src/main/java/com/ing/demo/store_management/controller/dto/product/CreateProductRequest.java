package com.ing.demo.store_management.controller.dto.product;

import com.ing.demo.store_management.model.product.base.Category;
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

    private GroceryProperties groceryProperties;
    private ClothingProperties clothingProperties;
    private ElectronicProperties electronicProperties;
}
