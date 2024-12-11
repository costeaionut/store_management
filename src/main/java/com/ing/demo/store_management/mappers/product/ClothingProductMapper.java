package com.ing.demo.store_management.mappers.product;

import com.ing.demo.store_management.controller.dto.product.properties.ClothingProperties;
import com.ing.demo.store_management.controller.dto.product.ProductRequest;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.model.product.concrete.Clothing;

public class ClothingProductMapper extends ProductMapper<ClothingProperties> {
    @Override
    protected ClothingProperties getProperties(ProductRequest dto) {
        return dto.getClothingProperties();
    }

    @Override
    public void mapFromDTO(ProductRequest dto, Product product) {
        super.mapFromDTO(dto, product);

        ClothingProperties properties = getProperties(dto);
        if (product instanceof Clothing clothing) {
            clothing.setSize(properties.getSize());
            clothing.setColor(properties.getColor());
            clothing.setMaterial(properties.getMaterial());
        }
    }

    @Override
    public void updateProductFields(Product oldP, Product newP) {
        super.updateProductFields(oldP, newP);

        if (oldP instanceof Clothing oldC && newP instanceof Clothing newC) {
            oldC.setSize(newC.getSize());
            oldC.setColor(newC.getColor());
            oldC.setMaterial(newC.getMaterial());
        }
    }
}
