package com.ing.demo.store_management.controller.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroceryProperties {
    private String expiryDate;
    private double weight;
    private boolean isPerishable;
}
