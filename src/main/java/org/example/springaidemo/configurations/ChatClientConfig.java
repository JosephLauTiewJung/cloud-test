package org.example.springaidemo.configurations;

import org.example.springaidemo.advisors.TokenLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    private final TokenLoggerAdvisor tokenLoggerAdvisor;

    public ChatClientConfig(TokenLoggerAdvisor tokenLoggerAdvisor) {
        this.tokenLoggerAdvisor = tokenLoggerAdvisor;
    }

    @Bean("googleChatClient")
    public ChatClient googleChatClient(GoogleGenAiChatModel googleGenAiChatModel) {
        ChatOptions chatOptions = ChatOptions
                .builder()
                .model("gemini-2.5-flash")
                .temperature(0.8)
                .build();

        return ChatClient.builder(googleGenAiChatModel)
                .defaultOptions(chatOptions)
                .defaultAdvisors(List.of(new SafeGuardAdvisor(List.of("bomb", "attack", "kill")), tokenLoggerAdvisor, new SimpleLoggerAdvisor()))
                .build();
    }
}
