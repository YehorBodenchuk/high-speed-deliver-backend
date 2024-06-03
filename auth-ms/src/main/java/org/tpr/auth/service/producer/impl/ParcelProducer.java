package org.tpr.auth.service.producer.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tpr.auth.service.producer.ProducerStrategy;

@Service
public class ParcelProducer implements ProducerStrategy {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.parcelQueueKey}")
    private String key;

    @Override
    public void sendMessage(Object message) {

    }
}
