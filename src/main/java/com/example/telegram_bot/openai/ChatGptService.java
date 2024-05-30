package com.example.telegram_bot.openai;

import com.example.telegram_bot.openai.api.ChatCompletionRequest;
import com.example.telegram_bot.openai.api.Message;
import com.example.telegram_bot.openai.api.OpenAIClient;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatGptService {
    private final OpenAIClient openAIClient;
    private final ChatGptHistory chatGptHistory;


    @Nonnull
    public String getResponseChatForUser(Long userId,
                                         String userTextInput
    ){
        chatGptHistory.createHistoryIfNotExist(userId);
        var history = chatGptHistory.addMessageToHistory(userId, Message.builder()
                .content(userTextInput)
                .role("user")
                .build());

        var request = ChatCompletionRequest.builder()
                .model("gpt-4")
                .messages(history.chatMessages())
                .build();
        var response = openAIClient.createChatCompletion(request);

        var messageFromGpt = response.choices().get(0).message();
        chatGptHistory.addMessageToHistory(userId,messageFromGpt);
        return messageFromGpt.content();
    }


}
