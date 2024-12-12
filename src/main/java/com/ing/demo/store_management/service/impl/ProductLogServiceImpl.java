package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.exception.log.ProductLogException;
import com.ing.demo.store_management.model.authentication.StoreUser;
import com.ing.demo.store_management.model.log.Log;
import com.ing.demo.store_management.model.log.OperationType;
import com.ing.demo.store_management.model.product.base.Product;
import com.ing.demo.store_management.repository.LogRepository;
import com.ing.demo.store_management.service.ProductLogService;
import com.ing.demo.store_management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductLogServiceImpl implements ProductLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductLogService.class);

    private final UserService userService;
    private final LogRepository logRepository;

    @Autowired
    public ProductLogServiceImpl(LogRepository logRepository, UserService userService) {
        this.logRepository = logRepository;
        this.userService = userService;
    }

    @Override
    public Log addLog(@Validated Product product, OperationType operation) {

        StoreUser user = userService.getAuthenticatedUser();

        if (user == null) {
            LOGGER.error("No authenticated user found.");
            throw new IllegalStateException("No authenticated user found.");
        }

        if (product == null) {
            LOGGER.error("Missing product to add");
            throw new IllegalArgumentException("Product can not be null.");
        }

        if (operation == null) {
            LOGGER.error("Operation type missing");
            throw new IllegalArgumentException("Operation type can not be null.");
        }

        try {
            Log log = new Log(user, product, operation);
            LOGGER.debug("Adding log: {}", log);
            return logRepository.save(log);
        } catch (Exception e) {
            LOGGER.error("Unexpected error while adding log for user: {}, product: {}, operation: {}", user, product, operation);
            throw new ProductLogException("Unexpected error occurred during product log creation", e.getCause());
        }
    }

    @Override
    public List<Log> retrieveLogsBasedOnFilters(List<Predicate<Log>> predicateList) {
        List<Log> allLogs = logRepository.findAll();

        Predicate<Log> combinedPredicate = predicateList.stream()
                .reduce(Predicate::and)
                .orElse(log -> true);

        return allLogs.stream()
                .filter(combinedPredicate)
                .collect(Collectors.toList());
    }
}
