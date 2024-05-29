package com.example.telegram_bot;

import com.example.telegram_bot.openai.ChatGptService;
import com.example.telegram_bot.openai.OpenAIClient;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {

    private final ChatGptService gptService;

    public TelegramBot(DefaultBotOptions options, String botToken, ChatGptService gptService) {
        super(options, botToken);
        this.gptService = gptService;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            var text = update.getMessage().getText();
            var chatId = update.getMessage().getChatId();

            //rebuild chatGptService
//            var chatCompletionResponse = openAIClient.createChatCompletion(text);
//            var textResponse = chatCompletionResponse.choices()
//                    .get(0).message().content();

            SendMessage sendMessage = new SendMessage(chatId.toString(), textResponse);
            //SendMessage sendMessage = new SendMessage(chatId.toString(), "++++textResponse++++++++");
            sendApiMethod(sendMessage);
        }
    }

    @Override
    public String getBotUsername() {
        return "My-test-bot";
    }
}
