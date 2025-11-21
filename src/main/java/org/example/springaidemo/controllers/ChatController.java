package org.example.springaidemo.controllers;

import org.example.springaidemo.advisors.TokenLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {
    private final ChatClient googleChatClient;
    private final TokenLoggerAdvisor tokenLoggerAdvisor;

    @Autowired
    public ChatController(@Qualifier("googleChatClient") ChatClient googleChatClient, TokenLoggerAdvisor tokenLoggerAdvisor) {
        this.googleChatClient = googleChatClient;
        this.tokenLoggerAdvisor = tokenLoggerAdvisor;
    }

    Resource promptTemplate;

    @GetMapping("/gemini/chat")

    public Flux<String> chat(@RequestParam String problem , @RequestParam String name) {
        promptTemplate = new ClassPathResource("/promptTemplates.st");
        return googleChatClient
                .prompt()
                .system("You are a helpful customer service assistant that reply the users emails in a professional tone.")
                .user(promptUserSpec -> promptUserSpec
                        .text(promptTemplate)
                        .params(Map.of(
                                "issue_description", problem,
                                "customer_name", name
                        )))
                .stream()
                .content();
    }
}
