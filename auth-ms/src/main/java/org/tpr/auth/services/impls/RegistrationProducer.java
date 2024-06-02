package org.tpr.auth.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tpr.auth.services.ProducerStrategy;

@Service
@RequiredArgsConstructor
public class RegistrationProducer implements ProducerStrategy {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.registrationQueueKey}")
    private String registrationKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(exchange, registrationKey, message);
    }
}
