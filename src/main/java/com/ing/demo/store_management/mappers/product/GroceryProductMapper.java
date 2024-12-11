package com.ing.demo.store_management.mappers.product;

import com.ing.demo.store_management.controller.dto.product.ProductRequest;
import com.ing.demo.store_management.controller.dto.product.properties.GroceryProperties;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.model.product.concrete.Grocery;

public class GroceryProductMapper extends ProductMapper<GroceryProperties> {
    @Override
    protected GroceryProperties getProperties(ProductRequest dto) {
        return dto.getGroceryProperties();
    }

    @Override
    public void mapFromDTO(ProductRequest dto, Product product) {
        super.mapFromDTO(dto, product);

        GroceryProperties properties = getProperties(dto);
        if (product instanceof Grocery grocery) {
            grocery.setWeight(properties.getWeight());
            grocery.setPerishable(properties.isPerishable());
            grocery.setExpiryDate(properties.getExpiryDate());
        }
    }

    @Override
    public void updateProductFields(Product oldP, Product newP) {
        super.updateProductFields(oldP, newP);
        if (oldP instanceof Grocery oldG && newP instanceof Grocery newG) {
            oldG.setWeight(newG.getWeight());
            oldG.setPerishable(newG.isPerishable());
            oldG.setExpiryDate(newG.getExpiryDate());
        }
    }
}
