package com.example.telegram_bot.openai;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatGptService {
    private final OpenAIClient openAIClient;


    @Nonnull
    public String getResponseChatForUser(Long userId,
                                         String userTextInput
    ){
        var request = ChatCompletionRequest.builder()
                .model("gpt-4")
                .messages(List.of(
                        Message.builder()
                                .content(userTextInput)
                                .role("user")
                                .build()
                ))
                .build();
        var response = openAIClient.createChatCompletion(request);
        return response.choices().get(0).message().content();
    }
}
