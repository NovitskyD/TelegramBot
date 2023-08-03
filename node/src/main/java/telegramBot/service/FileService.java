package telegramBot.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegramBot.entity.AppDocument;
import telegramBot.entity.AppPhoto;
import telegramBot.service.enums.LinkType;

public interface FileService {
    AppDocument processDoc(Message message);
    AppPhoto processPhoto(Message message);
    String generateLink(Long id, LinkType linkType);
}
