package com.autopilot.autopilot_agent.service;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiAnalysisService {

    private final OpenAiChatModel chatModel;

    public AiAnalysisService(@Value("${openai.api.key}") String apiKey) {
        this.chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini") // or "gpt-4-turbo"
                .build();
    }

    public String analyzeHealth(String healthJson) {
        String prompt = """
                You are an AI monitoring agent. Analyze this Spring Boot app health JSON and explain:
                - Current health status
                - Any components showing issues
                - Possible reasons or actions developer should take

                Health data:
                %s
                """.formatted(healthJson);

        return chatModel.chat(prompt);
    }
}


