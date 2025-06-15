package com.example.chat_26_5.service;

import com.example.chat_26_5.dto.ChatRequest;
import com.example.chat_26_5.dto.ChatRequest.Message;
import com.example.chat_26_5.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AiChatService {

    private final WebClient webClient;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    public AiChatService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAiResponse(String userMessage) {
        ChatRequest chatRequest = new ChatRequest(model, List.of(
                new Message("user", userMessage)
        ));


        ChatResponse response = webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block(); 

        if (response != null && response.choices != null && response.choices.length > 0) {
            return response.choices[0].message.content.trim();
        }
        return "Δεν πήρα απάντηση από το AI.";
    }
}
