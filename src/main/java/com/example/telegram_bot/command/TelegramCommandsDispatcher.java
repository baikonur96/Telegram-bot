package com.example.telegram_bot.command;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@AllArgsConstructor
public class TelegramCommandsDispatcher {
    private final List<TelegramCommandHandler> telegramCommandHandlerList;

    public BotApiMethod<?> processCommand(Message message){
        if (!isCommand(message)){
            throw new IllegalArgumentException("Not a comand passed");
        }

        var text = message.getText();
        var suitedHandler = telegramCommandHandlerList.stream()
                .filter(it -> it.getSupportedCommand().getCommandValue().equals(text))
                .findAny();
        if (suitedHandler.isEmpty()){
            var chatId = message.getChatId();
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("NotSupported command: command=%s".formatted(text))
                    .build();
//            var text = update.getMessage().getText();
//            var chatId = update.getMessage().getChatId();
//
//            SendMessage sendMessage = new SendMessage(chatId.toString(), gptGeneratedText);
//            sendApiMethod(sendMessage);
        }
        return suitedHandler.orElseThrow().processCommand(message);
    }

    public boolean isCommand(Message message){
        return  message.hasText()
                && message.getText().startsWith("/");
            }
}
