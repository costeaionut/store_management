package com.ing.demo.store_management.mappers.product;

import com.ing.demo.store_management.controller.dto.product.ProductRequest;
import com.ing.demo.store_management.controller.dto.product.properties.ProductProperties;
import com.ing.demo.store_management.model.product.base.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProductMapper<P extends ProductProperties> {

    protected final static Logger LOGGER = LoggerFactory.getLogger(ProductMapper.class);

    public Product mapFromDTO(ProductRequest dto) {
        Product product = getNewProduct();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());

        return product;
    }

    public void updateProductFields(Product oldP, Product newP) {
        oldP.setName(newP.getName());
        oldP.setDescription(newP.getDescription());
        oldP.setPrice(newP.getPrice());
        oldP.setCategory(newP.getCategory());
    }

    protected abstract P getProperties(ProductRequest dto);

    protected abstract Product getNewProduct();
}
