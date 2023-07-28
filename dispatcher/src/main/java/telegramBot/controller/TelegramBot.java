package telegramBot.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.configuration.TelegramBotConfig;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotConfig config;
    private final UpdateProcessor updateProcessor;

    public TelegramBot(TelegramBotConfig config, UpdateProcessor updateProcessor) {
        this.config = config;
        this.updateProcessor = updateProcessor;
    }

    @PostConstruct
    public void init(){
        updateProcessor.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateProcessor.processUpdate(update);
    }

    public void sendAnswerMessage(SendMessage message){
        if(message != null){
            try {
                execute(message);
            } catch (TelegramApiException e ){
                log.error(String.valueOf(e));
            }
        }
    }
}
