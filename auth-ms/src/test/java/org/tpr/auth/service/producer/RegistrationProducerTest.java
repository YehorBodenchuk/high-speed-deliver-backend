package org.tpr.auth.service.producer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.tpr.auth.controller.dto.rabbitmq.QueueRegistrationDto;
import org.tpr.auth.service.producer.impl.RegistrationProducer;
import org.tpr.auth.util.RabbitUtil;

class RegistrationProducerTest extends AbstractProducerTest {

    @InjectMocks
    private RegistrationProducer registrationProducer;

    @Override
    protected Object getTargetObject() {
        return registrationProducer;
    }

    @Test
    @DisplayName("Test send message in registration queue")
    void givenMessage_whenSendMessage_thenSendMessage() {
        QueueRegistrationDto message = RabbitUtil.getQueueRegistrationMessage();

        registrationProducer.sendMessage(message);

        Mockito.verify(rabbitTemplate).convertAndSend(EXCHANGE_NAME, KEY_NAME, message);
    }
}
