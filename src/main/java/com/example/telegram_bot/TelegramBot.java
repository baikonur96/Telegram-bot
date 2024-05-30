package com.example.telegram_bot;

import com.example.telegram_bot.command.TelegramCommandsDispatcher;
import com.example.telegram_bot.openai.ChatGptService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final ChatGptService gptService;
    private final TelegramCommandsDispatcher telegramCommandsDispatcher;


    public TelegramBot( String botToken,
                       ChatGptService gptService
                        TelegramCommandsDispatcher telegramCommandsDispatcher) {
        super(new DefaultBotOptions(), botToken);
        this.gptService = gptService;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        var methods = processUpdate(update);
        methods.forEach(it -> {
            try {
                sendApiMethod(it);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });


    }

    private List<BotApiMethod<?>> processUpdate(Update update){
        if (telegramCommandsDispatcher.isCommand(update)){
        }
        if (update.hasMessage() && update.getMessage().hasText()){
            var text = update.getMessage().getText();
            var chatId = update.getMessage().getChatId();

            var gptGeneratedText = gptService.getResponseChatForUser(chatId, text);
            SendMessage sendMessage = new SendMessage(chatId.toString(), gptGeneratedText);
            sendApiMethod(sendMessage);
        }
    }

    @Override
    public String getBotUsername() {
        return "My-test-bot";
    }
}
