package com.example.telegram_bot.telegram;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@Slf4j
@Service
public class TelegramFileService {

    private final String botToken;
    private final DefaultAbsSender telegramSender;

    public TelegramFileService(@Value("${bot.token}") String botToken,
                              @Lazy DefaultAbsSender telegramSender) {
        this.botToken = botToken;
        this.telegramSender = telegramSender;
    }

    @SneakyThrows
    public java.io.File getFile(String fileId){
        File file = telegramSender.execute(GetFile
                .builder()
                .fileId(fileId)
                .build());
        var urlToDownloadFile = file.getFileUrl(botToken);
        return getFileFromUrl(urlToDownloadFile);

    }

    @SneakyThrows
    private java.io.File getFileFromUrl(String urlToDownloadFile) {
        URL url = new URI(urlToDownloadFile).toURL();
        var fileTemp = java.io.File.createTempFile("telegram", ".ogg");
        try(InputStream inputStream = url.openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(fileTemp)){
            IOUtils.copy(inputStream,fileOutputStream);
          }catch (IOException e){
            log.error("Error while downloading file");
            throw new RuntimeException("Error while downloading file".formatted(e));
        }
        return fileTemp;

    }

    @SneakyThrows
    private byte[] getByteArrayFromUrl(String urlToDownloadFile) {
        URL url = new URI(urlToDownloadFile).toURL();

        try(InputStream inputStream = url.openStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }catch (IOException e){
            log.error("Error while downloading file");
            throw new RuntimeException("Error while downloading file".formatted(e));
        }

    }
}
