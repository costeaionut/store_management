package com.ing.demo.store_management.controller.dto.product.properties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroceryProperties extends ProductProperties {
    private String expiryDate;
    private double weight;
    private boolean isPerishable;
}
