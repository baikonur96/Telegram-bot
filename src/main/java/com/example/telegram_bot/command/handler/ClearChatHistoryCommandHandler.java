package com.example.telegram_bot.command.handler;

import com.example.telegram_bot.command.TelegramCommandHandler;
import com.example.telegram_bot.command.TelegramCommands;
import com.example.telegram_bot.openai.ChatGptHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class ClearChatHistoryCommandHandler implements TelegramCommandHandler {

    private final String CLEAR_MESSAGE = "Hello %s, History was cleared";

    private final ChatGptHistoryService chatGptHistory;

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        chatGptHistory.clearHistory(message.getChatId());
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(CLEAR_MESSAGE.formatted(message.getChat().getFirstName()))
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.CLEAR_COMMAND;
    }
}
