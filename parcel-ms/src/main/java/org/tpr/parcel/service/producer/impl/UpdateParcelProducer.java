package org.tpr.parcel.service.producer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tpr.parcel.controller.dto.rabbitmq.QueueUpdateParcelDto;
import org.tpr.parcel.service.producer.ProducerStrategy;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateParcelProducer implements ProducerStrategy<QueueUpdateParcelDto> {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.produce.updateParcel.key}")
    private String key;

    @Override
    public void sendMessage(QueueUpdateParcelDto message) {
        log.info("Sending request to update parcel queue...");
        rabbitTemplate.convertAndSend(exchange, key, message);
    }
}
