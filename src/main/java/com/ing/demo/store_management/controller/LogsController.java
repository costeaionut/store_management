package com.ing.demo.store_management.controller;

import com.ing.demo.store_management.mappers.log.LogFilterMapper;
import com.ing.demo.store_management.model.log.Log;
import com.ing.demo.store_management.service.ProductLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/log")
public class LogsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogsController.class);
    private final ProductLogService logService;

    @Autowired
    public LogsController(ProductLogService logService) {
        this.logService = logService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Log>> getLogs(@RequestParam Map<String, String> filters) {
        LOGGER.debug("Processing logs retrieval operation with filters: {}", filters);

        List<Predicate<Log>> predicates = LogFilterMapper.mapFilters(filters);
        List<Log> logs = logService.retrieveLogsBasedOnFilters(predicates);

        return ResponseEntity.ok(logs);
    }
}
