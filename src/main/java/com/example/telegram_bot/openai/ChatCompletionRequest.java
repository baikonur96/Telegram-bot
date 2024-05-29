package com.example.telegram_bot.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ChatCompletionRequest(String model,
                                    List<Message> messages
) {
}
