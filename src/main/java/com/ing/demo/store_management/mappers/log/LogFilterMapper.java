package com.ing.demo.store_management.mappers.log;

import com.ing.demo.store_management.controller.dto.log.LogsFilters;
import com.ing.demo.store_management.model.log.Log;
import com.ing.demo.store_management.model.log.OperationType;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LogFilterMapper {
    public static List<Predicate<Log>> mapFilters(Map<String, String> filters) {
        return filters.entrySet().stream()
                .map(set ->
                        new AbstractMap.SimpleEntry<>(
                                LogsFilters.valueOf(set.getKey().toUpperCase()),
                                set.getValue()))
                .map(set -> processFilter(set.getKey(), set.getValue()))
                .collect(Collectors.toList());
    }

    private static Predicate<Log> processFilter(LogsFilters type, String valueToFilterBy) {
        switch (type) {
            case USER_ID -> {
                return log -> log.getUser().getId().equals(Integer.valueOf(valueToFilterBy));
            }
            case PRODUCT_ID -> {
                return log -> log.getProductId().equals(Integer.valueOf(valueToFilterBy));
            }
            case OPERATION_TYPE -> {
                return log -> log.getOperation() == OperationType.valueOf(valueToFilterBy.toUpperCase());
            }
            // can enhance based on operational need
            default -> {
                return log -> true;
            }
        }
    }
}
