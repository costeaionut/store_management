package com.ing.demo.store_management.mappers.product;

import com.ing.demo.store_management.controller.dto.product.ProductRequest;
import com.ing.demo.store_management.controller.dto.product.properties.ElectronicProperties;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.model.product.concrete.Electronic;

public class ElectronicProductMapper extends ProductMapper<ElectronicProperties>{

    @Override
    public void mapFromDTO(ProductRequest dto, Product product) {
        super.mapFromDTO(dto, product);
        ElectronicProperties properties = getProperties(dto);
        if(product instanceof Electronic electronic) {
            electronic.setBrand(properties.getBrand());
            electronic.setWarrantyPeriod(properties.getWarrantyPeriod());
            electronic.setPowerRequirement(properties.getPowerRequirement());
        }
    }

    @Override
    public void updateProductFields(Product oldP, Product newP) {
        super.updateProductFields(oldP, newP);
        if(oldP instanceof Electronic oldE && newP instanceof Electronic newE) {
            oldE.setBrand(newE.getBrand());
            oldE.setWarrantyPeriod(newE.getWarrantyPeriod());
            oldE.setPowerRequirement(newE.getPowerRequirement());
        }
    }

    @Override
    protected ElectronicProperties getProperties(ProductRequest dto) {
        return dto.getElectronicProperties();
    }
}
