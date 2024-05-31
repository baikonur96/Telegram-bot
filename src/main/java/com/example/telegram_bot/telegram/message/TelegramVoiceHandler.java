package com.example.telegram_bot.telegram.message;

import com.example.telegram_bot.command.TelegramCommandsDispatcher;
import com.example.telegram_bot.openai.ChatGptService;
import com.example.telegram_bot.openai.api.TranscribeVoiceToTextService;
import com.example.telegram_bot.telegram.TelegramAsyncMessageSender;
import com.example.telegram_bot.telegram.TelegramFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
@Service
@AllArgsConstructor
public class TelegramVoiceHandler {

    private final ChatGptService gptService;
    private final TelegramFileService telegramFileService;
    private final TranscribeVoiceToTextService transcribeVoiceToTextService;


    public SendMessage processVoice(Message message) {
        var voice = message.getVoice();
        var fileId = voice.getFileId();
        var file = telegramFileService.getFile(fileId);
        var chatId = message.getChatId();

        var text = transcribeVoiceToTextService.transcribe(file);
        var gptGeneratedText = gptService.getResponseChatForUser(chatId, text);
        return new SendMessage(chatId.toString(), gptGeneratedText);

    }
}
