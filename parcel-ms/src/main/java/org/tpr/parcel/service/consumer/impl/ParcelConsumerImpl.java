package org.tpr.parcel.service.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.tpr.parcel.controller.dto.ParcelConsumerDto;
import org.tpr.parcel.modal.Parcel;
import org.tpr.parcel.service.ParcelService;
import org.tpr.parcel.service.consumer.ConsumerStrategy;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParcelConsumerImpl implements ConsumerStrategy<List<Parcel>, ParcelConsumerDto> {

    private final ParcelService parcelService;

    @RabbitListener(queues = "${rabbitmq.consume.parcelQueue}")
    @Override
    public List<Parcel> receiveMessage(ParcelConsumerDto message) {
        log.info("Received message from queue!");
        return parcelService.getAllParcelsByRecipientEmailOrPhone(message.getEmail(), message.getPhone());
    }
}
