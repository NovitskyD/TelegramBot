package telegramBot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import telegramBot.dao.RawDataDAO;
import telegramBot.dao.AppUserDAO;
import telegramBot.entity.AppDocument;
import telegramBot.entity.AppPhoto;
import telegramBot.entity.RawData;
import telegramBot.entity.AppUser;
import telegramBot.exceptions.UploadFileException;
import telegramBot.service.AppUserService;
import telegramBot.service.FileService;
import telegramBot.service.MainService;
import telegramBot.service.ProducerService;
import telegramBot.service.enums.LinkType;
import telegramBot.service.enums.ServiceCommands;

import java.util.Optional;

import static telegramBot.entity.enums.UserState.BASIC_STATE;
import static telegramBot.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;
import static telegramBot.service.enums.ServiceCommands.*;

@Service
@Slf4j
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    private final FileService fileService;
    private final AppUserService appUserService;

    public MainServiceImpl(RawDataDAO rawDataDAO,
                           ProducerService producerService,
                           AppUserDAO appUserDAO,
                           FileService fileService, AppUserService appUserService) {
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
        this.fileService = fileService;
        this.appUserService = appUserService;
    }

    public void processTextMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var userState = appUser.getState();
        String text = update.getMessage().getText();
        String output = "";

        var serviceCommand = ServiceCommands.fromCmd(text);
        if (CANCEL.equals(serviceCommand)) {
            output = cancelProcess(appUser);
        } else if (BASIC_STATE.equals(userState)) {
            output = processServiceCommand(appUser, text);
        } else if (WAIT_FOR_EMAIL_STATE.equals(userState)) {
            output = appUserService.setEmail(appUser, text);
        } else {
            log.error("Unknown user state: " + userState);
            output = "Unknown error! Type /cancel and try again!";
        }

        var chatId = update.getMessage().getChatId().toString();
        sendAnswer(output, chatId);
    }

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId().toString();
        if (isNotAllowedToSendContent(chatId, appUser)) {
            return;
        }
        try {
            AppDocument document = fileService.processDoc(update.getMessage());
            String link = fileService.generateLink(document.getId(), LinkType.GET_DOC);
            String answer = "Document uploaded successfully! " +
                    "Download link: " + link;

            sendAnswer(answer, chatId);
        } catch (UploadFileException e){
            log.error(String.valueOf(e));
            String error = "Unsuccessful attempt to upload file. Please try again later";
            sendAnswer(error, chatId);
        }
    }

    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId().toString();
        if (isNotAllowedToSendContent(chatId, appUser)){
            return;
        }
        try {
            AppPhoto photo = fileService.processPhoto(update.getMessage());
            String link = fileService.generateLink(photo.getId(), LinkType.GET_PHOTO);
            String answer = "Photo uploaded successfully! " +
                    "Photo link: " + link;

            sendAnswer(answer, chatId);
        } catch (UploadFileException e){
            log.error(String.valueOf(e));
            String error = "Unsuccessful attempt to upload photo. Please try again later";
            sendAnswer(error, chatId);
        }
    }

    private boolean isNotAllowedToSendContent(String chatId, AppUser appUser) {
        var userState = appUser.getState();
        if (!appUser.getIsActive()) {
            var error = "Register or activate your account to download content";
            sendAnswer(error, chatId);
            return true;
        } else if (!BASIC_STATE.equals(userState)) {
            var error = "Cancel the current command with /cancel to send files";
            sendAnswer(error, chatId);
            return true;
        }
        return false;
    }

    private void sendAnswer(String output, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);
        producerService.produceAnswer(sendMessage);
    }

    private String processServiceCommand(AppUser appUser, String cmd) {
        var serviceCommand = ServiceCommands.fromCmd(cmd);
        if (serviceCommand == null) {
            log.debug("Unknown command: " + cmd);
            return "Unknown command! To see a list of available commands, type /help";
        }
        return switch (serviceCommand){
            case REGISTRATION -> appUserService.registerUser(appUser);
            case HELP -> help();
            case START -> "Greetings! To see a list of available commands, type /help";
            default -> "Unknown command! To see a list of available commands, type /help";
        };
    }

    private String help() {
        return "List of available commands:\n" +
                "/cancel - canceling the current command\n" +
                "/registration - user registration\n";
    }

    private String cancelProcess(AppUser appUser) {
        appUser.setState(BASIC_STATE);
        appUserDAO.save(appUser);
        return "Command canceled!";
    }

    private AppUser findOrSaveAppUser(Update update){
        User telegramUser = update.getMessage().getFrom();
        Optional<AppUser> persistentAppUser = appUserDAO.findAppUserByTelegramUserid(telegramUser.getId());
        if (persistentAppUser.isEmpty()){
            AppUser transientAppUser = AppUser.builder()
                    .telegramUserid(telegramUser.getId())
                    .username(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .isActive(false)
                    .state(BASIC_STATE)
                    .build();
            return appUserDAO.save(transientAppUser);
        }
        return persistentAppUser.get();
    }

    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .event(update)
                .build();

        rawDataDAO.save(rawData);
    }
}
