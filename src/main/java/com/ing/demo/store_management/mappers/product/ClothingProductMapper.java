package com.ing.demo.store_management.mappers.product;

import com.ing.demo.store_management.controller.dto.product.ProductRequest;
import com.ing.demo.store_management.controller.dto.product.properties.ClothingProperties;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.model.product.concrete.Clothing;

public class ClothingProductMapper extends ProductMapper<ClothingProperties> {
    @Override
    protected ClothingProperties getProperties(ProductRequest dto) {
        return dto.getClothingProperties();
    }

    @Override
    protected Product getNewProduct() {
        return new Clothing();
    }

    @Override
    public Clothing mapFromDTO(ProductRequest dto) {
        Clothing clothing = (Clothing) super.mapFromDTO(dto);

        ClothingProperties properties = getProperties(dto);
        clothing.setSize(properties.getSize());
        clothing.setColor(properties.getColor());
        clothing.setMaterial(properties.getMaterial());

        return clothing;
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
