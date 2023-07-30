package telegramBot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegramBot.service.ProducerService;

import static model.RabbitQueue.ANSWER_MESSAGE;

@Service
@Slf4j
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        log.debug("sendMessageText: " + sendMessage.getText());
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }
}
