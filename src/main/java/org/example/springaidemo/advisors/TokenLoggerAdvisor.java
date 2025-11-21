package org.example.springaidemo.advisors;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class TokenLoggerAdvisor implements CallAdvisor {

    private static final Logger log = Logger.getLogger(TokenLoggerAdvisor.class.getName());

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        Usage usage = chatClientResponse.chatResponse().getMetadata().getUsage();
        log.info("Total tokens used: " + usage.getTotalTokens());
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "TokenLoggerAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
