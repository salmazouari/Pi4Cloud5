package com.example.skillbridge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")            // Apply to all endpoints
                .allowedOrigins("http://localhost:4200") // Angular dev server
                .allowedMethods("*")          // Allow all HTTP methods
                .allowedHeaders("*")          // Allow all headers
                .allowCredentials(true)       // Allow cookies/authentication
                .maxAge(3600);               // Cache preflight requests for 1 hour
    }
}