package com.ing.demo.store_management.controller.dto.product.properties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElectronicProperties extends ProductProperties {
    private String brand;
    private int warrantyPeriod;
    private String powerRequirement;
}
