package com.example.telegram_bot.openai;

import com.example.telegram_bot.openai.api.Message;
import lombok.Builder;

import java.util.List;

@Builder
public record ChatHistory(
        List<Message> chatMessages
) {
}
