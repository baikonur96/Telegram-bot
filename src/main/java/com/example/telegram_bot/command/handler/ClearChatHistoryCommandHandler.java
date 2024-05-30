package com.example.telegram_bot.command.handler;

import com.example.telegram_bot.command.TelegramCommandHandler;
import com.example.telegram_bot.command.TelegramCommands;
import com.example.telegram_bot.openai.ChatGptHistory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ClearChatHistoryCommandHandler implements TelegramCommandHandler {

    private final ChatGptHistory chatGptHistory;

    @Override
    public BotApiMethod<?> processCommand(Update update) {
        chatGptHistory.createHistory(userId);
        return null;
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.CLEAR_COMMAND;
    }
}
