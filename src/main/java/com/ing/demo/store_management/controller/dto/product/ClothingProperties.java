package com.ing.demo.store_management.controller.dto.product;

import com.ing.demo.store_management.model.product.base.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClothingProperties {
    private Size size;
    private String material;
    private String color;
}
