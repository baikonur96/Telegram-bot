package com.example.telegram_bot.openai.api;

import lombok.Builder;

import java.io.File;

@Builder
public record CreateTranslationRequest(File audioFile, String model) {
}
