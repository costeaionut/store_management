package com.ing.demo.store_management.service.impl;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BasicSanitizerTests {


    BasicInputSanitizer sanitizer = new BasicInputSanitizer();

    @Test
    public void testValidInputSanitizer() {
        String validInput = "NoCharactersToEscape";
        String sanitizedInput = sanitizer.sanitizeInput(validInput);

        assertEquals(validInput, sanitizedInput);
    }

    @Test
    public void testCharactersEscapingSanitizer() {
        List<String> inputs = List.of("te'st", "te\"st", "te;st", "te\\st", "te%st", "te_st");
        List<String> expected = List.of("te\\\\'st", "te\\\\\"st", "te\\\\;st", "te\\\\st", "te\\\\%st", "te\\_st");

        for (int inputIndex = 0; inputIndex < inputs.size(); inputIndex++) {
            assertEquals(expected.get(inputIndex), sanitizer.sanitizeInput(inputs.get(inputIndex)));
        }
    }

    @Test
    public void testNullInput() {
        assertNull(sanitizer.sanitizeInput(null));
    }
}
