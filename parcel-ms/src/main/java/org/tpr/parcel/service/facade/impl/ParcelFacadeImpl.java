package org.tpr.parcel.service.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tpr.parcel.controller.dto.CreateParcelDto;
import org.tpr.parcel.controller.dto.UpdateParcelDto;
import org.tpr.parcel.controller.dto.rabbitmq.QueueUpdateParcelDto;
import org.tpr.parcel.modal.Parcel;
import org.tpr.parcel.service.ParcelService;
import org.tpr.parcel.service.facade.ParcelFacade;
import org.tpr.parcel.service.producer.impl.UpdateParcelProducer;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParcelFacadeImpl implements ParcelFacade {

    private final ParcelService parcelService;

    private final UpdateParcelProducer updateParcelProducer;

    @Override
    public Parcel updateParcel(UpdateParcelDto request, String id) {
        Parcel parcel = parcelService.update(request, id);

        QueueUpdateParcelDto rabbitRequest = QueueUpdateParcelDto.builder()
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .status(request.getStatus())
                .recipientEmail(parcel.getRecipient().getEmail())
                .senderEmail(parcel.getSender().getEmail())
                .build();

        updateParcelProducer.sendMessage(rabbitRequest);

        return parcel;
    }

    @Override
    public Parcel createParcel(CreateParcelDto request) {
        Parcel parcel = parcelService.createParcel(request);

        QueueUpdateParcelDto rabbitRequest = QueueUpdateParcelDto.builder()
                .status(parcel.getStatus())
                .recipientEmail(parcel.getRecipient().getEmail())
                .senderEmail(parcel.getSender().getEmail())
                .build();

        updateParcelProducer.sendMessage(rabbitRequest);

        return parcel;
    }
}
