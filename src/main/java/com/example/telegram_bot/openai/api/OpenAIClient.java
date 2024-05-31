package com.example.telegram_bot.openai.api;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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


        HttpEntity<ChatCompletionRequest> httpEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<ChatCompletionResponse> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST,
                httpEntity,
                ChatCompletionResponse.class);
        return responseEntity.getBody();
    }
    @SneakyThrows
    public TranscriptionRespose createTranslation(CreateTranslationRequest request){

        String url = "https://api.openai.com/v1/audio/translations";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authoriztion", "Bearer " + token);
        httpHeaders.set("Content-type", "multipart/form-data");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(request.audioFile()));
        body.add("model", request.model());

        var httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<TranscriptionRespose> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST,
                httpEntity,
                TranscriptionRespose.class);
        return responseEntity.getBody();

    }


}
