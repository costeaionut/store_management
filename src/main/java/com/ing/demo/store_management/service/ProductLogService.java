package com.ing.demo.store_management.service;

import com.ing.demo.store_management.model.log.Log;
import com.ing.demo.store_management.model.log.OperationType;
import com.ing.demo.store_management.model.product.base.Product;

public interface ProductLogService {
    Log addLog(Product product, OperationType operation);
}
