package com.example.skillbridge.controllers;

import com.example.skillbridge.services.OllamaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/blog")
//@CrossOrigin(origins = "*")
public class BlogController {

    private final OllamaService ollamaService;

    public BlogController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/generate")
    public Mono<ResponseEntity<Map<String, Object>>> generateBlogPost(@RequestBody Map<String, String> payload) {
        String idea = payload.get("idea");
        if (idea == null || idea.trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest()
                    .body(Map.of("error", (Object) "Missing required field: idea")));
        }

        String formattedPrompt = String.format("""
            [INST] <<SYS>>
            You are a professional technical writer. Generate a blog post in markdown format with these requirements:
            - Use proper heading structure
            - Include sections for Introduction, Main Content, and Conclusion
            - Use technical terms where appropriate
            - Keep paragraphs under 5 sentences
            <</SYS>>
            Write about: %s
            [/INST]""", idea.trim());

        return ollamaService.generateContent(formattedPrompt)
                .map(content -> ResponseEntity.ok()
                        .body(Map.of("content", (Object) content)))
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", (Object) ("Failed to generate content: " + e.getMessage())))));
    }

    @GetMapping("/check-ollama")
    public Mono<ResponseEntity<String>> checkOllamaConnection() {
        return ollamaService.getWebClient().get()
                .uri("/api/tags")
                .retrieve()
                .toEntity(String.class)
                .map(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return ResponseEntity.ok("Ollama is running. Available models: " + response.getBody());
                    }
                    return ResponseEntity.status(response.getStatusCode())
                            .body("Ollama connection check failed: " + response.getBody());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Ollama connection error: " + e.getMessage())));
    }
}