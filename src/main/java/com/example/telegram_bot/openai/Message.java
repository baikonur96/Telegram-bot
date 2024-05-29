package com.example.telegram_bot.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Message(@JsonProperty("role") String role,
        @JsonProperty("content") String content) {



}
