package com.ing.demo.store_management.controller.dto.product.properties;

import com.ing.demo.store_management.model.product.base.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClothingProperties extends ProductProperties {
    private Size size;
    private String material;
    private String color;
}
