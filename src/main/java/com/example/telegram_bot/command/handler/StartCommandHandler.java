package com.example.telegram_bot.command.handler;

import com.example.telegram_bot.command.TelegramCommandHandler;
import com.example.telegram_bot.command.TelegramCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommandHandler implements TelegramCommandHandler {

    private final String HELLO_MESSAGE = "Hello %s, I am bot";

    @Override
    public BotApiMethod<?> processCommand(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(HELLO_MESSAGE.formatted(update.getMessage().getChat().getFirstName()))
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.START_COMMAND;
    }
}
