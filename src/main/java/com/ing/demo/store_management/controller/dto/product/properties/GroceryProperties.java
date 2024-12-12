package com.ing.demo.store_management.controller.dto.product.properties;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GroceryProperties extends ProductProperties {
    private Date expiryDate;
    private double weight;
    private boolean isPerishable;
}
