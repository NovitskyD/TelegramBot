package telegramBot.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegramBot.entity.AppDocument;

public interface FileService {
    AppDocument processDoc(Message message);
}
