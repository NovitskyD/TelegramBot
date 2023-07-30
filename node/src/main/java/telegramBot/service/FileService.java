package telegramBot.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegramBot.entity.AppDocument;
import telegramBot.entity.AppPhoto;

public interface FileService {
    AppDocument processDoc(Message message);
    AppPhoto processPhoto(Message message);
}
