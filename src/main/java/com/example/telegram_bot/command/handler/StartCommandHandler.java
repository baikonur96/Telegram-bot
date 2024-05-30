package com.example.telegram_bot.command.handler;

import com.example.telegram_bot.command.TelegramCommandHandler;
import com.example.telegram_bot.command.TelegramCommands;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommandHandler implements TelegramCommandHandler {
    @Override
    public BotApiMethod<?> processCommand(Update update) {
        return null;
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.START_COMMAND;
    }
}
