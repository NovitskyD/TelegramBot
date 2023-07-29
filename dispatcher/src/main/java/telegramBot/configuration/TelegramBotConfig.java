package telegramBot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.controller.TelegramBot;

@Configuration
@Data
@PropertySource("classpath:bot.properties")
public class TelegramBotConfig {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
}
