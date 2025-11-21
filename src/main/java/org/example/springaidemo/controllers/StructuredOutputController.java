package org.example.springaidemo.controllers;

import org.example.springaidemo.models.City;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gemini")
public class StructuredOutputController {
    private ChatClient googleChatClient;

    public StructuredOutputController(ChatClient googleChatClient) {
        this.googleChatClient = googleChatClient;
    }

    @GetMapping("/structured")
    public Map<String, Object> getCity(@RequestParam String country) {
        return googleChatClient
                .prompt()
                .user("Tell me ten city in the country: " + country + "with their populations")
                .call()
                .entity(new MapOutputConverter());
    }

    @GetMapping("/cities-details")
    public List<City> getCities(@RequestParam List<String> cities) {
        return googleChatClient
                .prompt()
                .user("Give me the info of: " + cities)
                .call()
                .entity(new ParameterizedTypeReference<List<City>>() {
                });
    }
}
