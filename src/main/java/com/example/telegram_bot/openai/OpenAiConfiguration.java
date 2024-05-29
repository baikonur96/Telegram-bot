package com.example.telegram_bot.openai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration

public class OpenAiConfiguration {

    @Bean
    public OpenAIClient openAIClient(
            @Value("${openai.token}") String botToken,
            RestTemplateBuilder restTemplateBuilder
    ){
        return new OpenAIClient(botToken, restTemplateBuilder.build());
    }
}
