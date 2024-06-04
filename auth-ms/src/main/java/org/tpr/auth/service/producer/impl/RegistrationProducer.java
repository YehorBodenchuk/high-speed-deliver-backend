package org.tpr.auth.service.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tpr.auth.controller.dto.rabbitmq.QueueRegistrationDto;
import org.tpr.auth.service.producer.ProducerStrategy;

@Service
@RequiredArgsConstructor
public class RegistrationProducer implements ProducerStrategy<Void, QueueRegistrationDto> {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.registrationQueueKey}")
    private String key;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public Void sendMessage(QueueRegistrationDto message) {
        rabbitTemplate.convertAndSend(exchange, key, message);
        return null;
    }

//    @Override
//    public void sendMessage(Object message) {
//        rabbitTemplate.convertAndSend(exchange, key, message);
//    }
//
//    @Override
//    public T sendMessage(String message) {
//        return null;
//    }
}
