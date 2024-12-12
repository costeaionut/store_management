package com.ing.demo.store_management.controller.dto.log;

import com.ing.demo.store_management.model.log.Log;
import com.ing.demo.store_management.model.log.OperationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogResponseDTO {
    private int operator;
    private int product;
    private LocalDateTime timestamp;
    private OperationType operation;

    public LogResponseDTO(Log log) {
        this.operator = log.getUser().getId();
        this.product = log.getProductId();
        this.timestamp = log.getTimestamp();
        this.operation = log.getOperation();
    }
}
