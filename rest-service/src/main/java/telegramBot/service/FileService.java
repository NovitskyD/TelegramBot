package telegramBot.service;

import org.springframework.core.io.FileSystemResource;
import telegramBot.entity.AppDocument;
import telegramBot.entity.AppPhoto;
import telegramBot.entity.BinaryContent;

public interface FileService {
    AppDocument getDocument(String hash);
    AppPhoto getPhoto(String hash);
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
