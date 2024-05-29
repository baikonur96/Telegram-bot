package com.example.telegram_bot.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Choice(@JsonProperty("message") Message message) {
}
