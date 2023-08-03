package telegramBot.service;

import telegramBot.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
