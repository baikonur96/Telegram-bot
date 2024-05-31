package com.example.telegram_bot.telegram.message;

import com.example.telegram_bot.command.TelegramCommandsDispatcher;
import com.example.telegram_bot.telegram.TelegramAsyncMessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Service
@AllArgsConstructor
public class TelegramUpdateMessageHandler {

    private final TelegramCommandsDispatcher telegramCommandsDispatcher;
    private final TelegramAsyncMessageSender telegramAsyncMessageSender;
    private final TelegramTextHandler telegramTextHandler;
    private final TelegramVoiceHandler telegramVoiceHandler;



    public BotApiMethod<?> handleMessage(Message message){
        if (telegramCommandsDispatcher.isCommand(message)) {
            return telegramCommandsDispatcher.processCommand(message);
        }
        var chatId = message.getChatId().toString();
        if (message.hasVoice() || message.hasText()){
            telegramAsyncMessageSender.sendMessageAsync(
                   chatId,
                   () -> handleMessageAsync(message),
                   this::getErrorMessage
            );
        }
        return null;

//
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            telegramAsyncMessageSender.sendMessageAsync(chatId,
//                    () -> processTextMessage(update.getMessage()),
//                    this::getErrorMessageForUser
//            );
//        }
//        if (update.hasMessage() && update.getMessage().hasVoice()) {
//            telegramAsyncMessageSender.sendMessageAsync(chatId,
//                    () -> processVoiceMessage(update.getMessage()),
//                    this::getErrorMessageForUser
//            );
//        }
//        return null;
    }

    private SendMessage handleMessageAsync(Message message) {
        if (message.hasVoice()){
            return telegramVoiceHandler.processVoice(message);
        }else {
            return telegramTextHandler.processTextMessage(message);
        }


    }

    private SendMessage getErrorMessage(Throwable throwable) {
        log.error("Error", throwable);
        return SendMessage.builder()
                .text("Error, try again later")
                .build();

    }
}

