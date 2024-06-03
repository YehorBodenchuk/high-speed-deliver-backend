package org.tpr.parcel.services.producer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.tpr.parcel.controllers.dto.UserConsumerDto;
import org.tpr.parcel.services.producer.ProducerStrategy;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoProducerImpl implements ProducerStrategy<UserConsumerDto, String> {

    @Value("${rabbitmq.userInfoQueueKey}")
    private String key;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public UserConsumerDto sendMessage(String email) {
        return rabbitTemplate.convertSendAndReceiveAsType(exchange, key, email, new ParameterizedTypeReference<>() {});
    }
}
