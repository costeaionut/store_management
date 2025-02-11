package com.ing.demo.store_management.mappers.product;

import com.ing.demo.store_management.controller.dto.product.ProductRequestDTO;
import com.ing.demo.store_management.controller.dto.product.properties.ElectronicProperties;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.model.product.concrete.Electronic;

public class ElectronicProductMapper extends ProductMapper<ElectronicProperties> {

    @Override
    public Electronic mapFromDTO(ProductRequestDTO dto) {
        Electronic electronic = (Electronic) super.mapFromDTO(dto);

        ElectronicProperties properties = getProperties(dto);
        electronic.setBrand(properties.getBrand());
        electronic.setWarrantyPeriod(properties.getWarrantyPeriod());
        electronic.setPowerRequirement(properties.getPowerRequirement());

        LOGGER.debug("Mapped electronics product from dto: {}", dto);
        return electronic;
    }

    @Override
    public void updateProductFields(Product oldP, Product newP) {
        super.updateProductFields(oldP, newP);
        if (oldP instanceof Electronic oldE && newP instanceof Electronic newE) {
            oldE.setBrand(newE.getBrand());
            oldE.setWarrantyPeriod(newE.getWarrantyPeriod());
            oldE.setPowerRequirement(newE.getPowerRequirement());
        }
        LOGGER.debug("Updated electronics product to new values: {}", newP);
    }

    @Override
    protected ElectronicProperties getProperties(ProductRequestDTO dto) {
        return dto.getElectronicProperties();
    }

    @Override
    protected Product getNewProduct() {
        return new Electronic();
    }
}
