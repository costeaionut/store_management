package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.service.InputSanitizer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BasicInputSanitizer implements InputSanitizer {

    private static final Map<String, String> ESCAPING_RULES = new HashMap<>();

    static {
        ESCAPING_RULES.put("'", "\\'");
        ESCAPING_RULES.put("\"", "\\\"");
        ESCAPING_RULES.put(";", "\\;");
        ESCAPING_RULES.put("\\", "\\\\");
        ESCAPING_RULES.put("%", "\\%");
        ESCAPING_RULES.put("_", "\\_");
    }

    @Override
    public String sanitizeInput(String rawInput) {

        if (rawInput == null) {
            return null;
        }

        String escapedInput = rawInput;

        for (Map.Entry<String, String> entry : ESCAPING_RULES.entrySet()) {
            escapedInput = escapedInput.replace(entry.getKey(), entry.getValue());
        }

        return escapedInput;
    }
}
