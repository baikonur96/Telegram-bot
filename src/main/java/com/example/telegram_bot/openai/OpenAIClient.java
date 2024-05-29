package com.example.telegram_bot.openai;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class OpenAIClient {
    private final String token;

    private final RestTemplate restTemplate;

    public ChatCompletionResponse createChatCompletion(ChatCompletionRequest request){
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authoriztion", "Bearer " + token);
        httpHeaders.set("Content-type", "application/json");


        HttpEntity<String> httpEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<ChatCompletionResponse> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST,
                httpEntity,
                ChatCompletionResponse.class);
        return responseEntity.getBody();
    }


}
