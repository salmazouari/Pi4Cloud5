package com.example.skillbridge.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Map;

@Service
public class OllamaService {

    private final WebClient webClient;
    private final String modelName;

    public OllamaService(
            @Value("${ollama.api.url}") String apiUrl,
            @Value("${ollama.model}") String modelName
    ) {
        this.modelName = modelName;
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofMinutes(5))
                ))  // Fixed: Close ReactorClientHttpConnector properly
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> generateContent(String prompt) {
        Map<String, Object> body = Map.of(
                "model", modelName,
                "prompt", prompt,
                "stream", false
        );

        return webClient.post()
                .uri("/api/generate")
                .bodyValue(body)
                .retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(
                                        "Ollama API Error [" + response.statusCode() + "]: " + errorBody
                                )))
                )
                .bodyToMono(Map.class)
                .flatMap(responseMap -> {
                    if (responseMap.containsKey("response")) {
                        return Mono.just(responseMap.get("response").toString());
                    } else if (responseMap.containsKey("error")) {
                        return Mono.error(new RuntimeException(responseMap.get("error").toString()));
                    }
                    return Mono.error(new RuntimeException("Unexpected response format from Ollama"));
                });
    }

    public WebClient getWebClient() {
        return webClient;
    }
}