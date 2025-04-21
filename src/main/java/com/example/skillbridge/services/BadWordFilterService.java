package com.example.skillbridge.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class BadWordFilterService {

    private final RestTemplate restTemplate;

    public BadWordFilterService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public boolean containsBadWords(String text) {
        try {
            // Properly encode the text parameter
            String encodedText = UriUtils.encode(text, StandardCharsets.UTF_8);
            String url = "https://www.purgomalum.com/service/containsprofanity?text=" + encodedText;

            // Add error handling
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return Boolean.parseBoolean(response.getBody());
            }
            throw new RuntimeException("Bad word filter service returned: " + response.getStatusCode());

        } catch (RestClientException e) {
            throw new RuntimeException("Failed to check for bad words", e);
        }
    }
}