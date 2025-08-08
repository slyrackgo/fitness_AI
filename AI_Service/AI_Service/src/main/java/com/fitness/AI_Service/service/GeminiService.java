package com.fitness.AI_Service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );

        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
//
//package com.fitness.AI_Service.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.util.Map;
//
//@Service
//public class GeminiService {
//
//    private final WebClient webClient;
//
//    @Value("${gemini.api.url}")
//    private String geminiApiUrl;
//
//    @Value("${gemini.api.key}")
//    private String geminiApiKey;
//
//    public GeminiService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    public String getAnswer(String question) {
//        Map<String, Object> requestBody = Map.of(
//                "contents", new Object[] {
//                        Map.of("parts", new Object[] {
//                                Map.of("text", question)
//                        })
//                }
//        );
//
//        try {
//            return webClient.post()
//                    .uri(geminiApiUrl) // Use just the base URL from application.yml
//                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer " + geminiApiKey) // ✅ Add key correctly
//                    .bodyValue(requestBody)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .onErrorResume(ex -> {
//                        // Log or return a fallback response
//                        System.err.println("Error from Gemini API: " + ex.getMessage());
//                        return Mono.just("Gemini API error: " + ex.getMessage());
//                    })
//                    .block();
//
//        } catch (Exception e) {
//            return "Exception while calling Gemini API: " + e.getMessage();
//        }
//    }
//}
//
//package com.fitness.AI_Service.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.util.Map;
//
//@Service
//public class GeminiService {
//
//    private final WebClient webClient;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Value("${gemini.api.url}")
//    private String geminiApiUrl;
//
//    @Value("${gemini.api.key}")
//    private String geminiApiKey;
//
//    public GeminiService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    public String getAnswer(String question) {
//        Map<String, Object> requestBody = Map.of(
//                "contents", new Object[]{
//                        Map.of("parts", new Object[]{
//                                Map.of("text", question)
//                        })
//                }
//        );
//
//        try {
//            String rawResponse = webClient.post()
//                    .uri(geminiApiUrl)
//                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer " + geminiApiKey)
//                    .bodyValue(requestBody)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .onErrorResume(ex -> {
//                        System.err.println("Gemini API error: " + ex.getMessage());
//                        return Mono.just("{\"error\":\"Gemini API failed\"}");
//                    })
//                    .block();
//
//            JsonNode root = objectMapper.readTree(rawResponse);
//            JsonNode textNode = root.path("candidates").path(0)
//                    .path("content").path("parts").path(0)
//                    .path("text");
//
//            return textNode.asText("No answer received.");
//
//        } catch (Exception e) {
//            System.err.println("Exception while processing Gemini API response: " + e.getMessage());
//            return "Error: Failed to parse Gemini response.";
//        }
//    }
//}


//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.Map;
//
//@Service
//public class GeminiService {
//
//    private final WebClient webClient;
//
//    @Value("${gemini.api.url}")
//    private String geminiApiUrl;
//
//    @Value("${gemini.api.key}")
//    private String geminiApiKey;
//
//    public GeminiService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    public String getAnswer(String question) {
//        Map<String, Object> requestBody = Map.of(
//                "contents", new Object[] {
//                        Map.of("parts", new Object[]{
//                                Map.of("text", question)
//                        })
//                }
//        );
//
//        String response = webClient.post()
//                .uri(geminiApiUrl + geminiApiKey)
//                .header("Content-Type", "application/json")
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        return response;
//    }
//}