package telegramBot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegramBot.controller.UpdateProcessor;
import telegramBot.service.AnswerConsumer;

import static model.RabbitQueue.ANSWER_MESSAGE;

@Service
@Slf4j
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateProcessor updateProcessor;

    public AnswerConsumerImpl(UpdateProcessor updateProcessor) {
        this.updateProcessor = updateProcessor;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        log.debug("SendMessageText: " + sendMessage.getText());
        updateProcessor.setView(sendMessage);
    }
}
