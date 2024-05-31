package com.example.telegram_bot.openai.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@AllArgsConstructor
public class TranscribeVoiceToTextService {

    private final OpenAIClient openAIClient;

    public String transcribe (File audioFile){

        var response = openAIClient.createTranslation(CreateTranslationRequest.
                builder()
                .audioFile(audioFile)
                        .model("whisper-1")
                .build());
        return response.text();
    }
}
