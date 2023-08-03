package telegramBot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import telegramBot.utils.CryptoTool;

@Configuration
@PropertySource("classpath:bot.properties")
public class NodeConfig {
    @Value("${salt}")
    private String salt;

    @Bean
    public CryptoTool getCryptoTool() {
        return new CryptoTool(salt);
    }
}
