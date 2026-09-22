package com.company.mobile.utils;

import com.company.mobile.exceptions.FrameworkException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {}

    public static JsonNode readJsonFile(String filePath) {
        try {
            return OBJECT_MAPPER.readTree(new File(filePath));
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON file: " + filePath, e);
        }
    }
}
