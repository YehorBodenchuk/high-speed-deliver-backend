package org.tpr.auth.service.producer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.tpr.auth.controller.dto.parcel.ParcelDto;
import org.tpr.auth.controller.dto.rabbitmq.QueueParcelDto;
import org.tpr.auth.service.producer.ProducerStrategy;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParcelProducer implements ProducerStrategy<List<ParcelDto>, QueueParcelDto> {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.parcelQueueKey}")
    private String key;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public List<ParcelDto> sendMessage(QueueParcelDto message) {
        log.info(String.format("Loading user parcels with email: %s", message.getEmail()));
        return rabbitTemplate.convertSendAndReceiveAsType(exchange, key, message, new ParameterizedTypeReference<>(){});
    }
}
