package com.auth.vibe.login.utils;

import com.auth.vibe.login.exception.ApiExpection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class JsonFileReader {
        public static Map<String, Object> readJson(String fileName) {
            try {
                // Load the JSON file from resources
                var resource = new ClassPathResource("json/" + fileName);
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(resource.getInputStream(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApiExpection("404");
            }
        }
    }


