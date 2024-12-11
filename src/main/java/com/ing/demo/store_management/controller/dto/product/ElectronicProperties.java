package com.ing.demo.store_management.controller.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElectronicProperties {
    private String brand;
    private int warrantyPeriod;
    private String powerRequirement;
}
