package org.tpr.auth.util;

import org.tpr.auth.controller.dto.parcel.ParcelDto;
import org.tpr.auth.controller.dto.parcel.ParcelHistory;
import org.tpr.auth.controller.dto.parcel.ParcelLocation;
import org.tpr.auth.controller.dto.parcel.ParcelPerson;
import org.tpr.auth.controller.dto.rabbitmq.QueueParcelDto;
import org.tpr.auth.controller.dto.rabbitmq.QueueRegistrationDto;

import java.util.Date;
import java.util.List;

public final class RabbitUtil {

    public static QueueRegistrationDto getQueueRegistrationMessage() {
        return QueueRegistrationDto.builder()
                .firstName("John")
                .email("john.doe@gmail.com")
                .build();
    }

    public static QueueParcelDto getQueueParcelMessage() {
        return QueueParcelDto.builder()
                .email("john.doe@gmail.com")
                .phone("+380993333333")
                .build();
    }

    public static List<ParcelDto> getQueueParcelResponse() {
        ParcelPerson sender = ParcelPerson.builder()
                .email("john.doe@gmail.com")
                .phone("+380993333333")
                .build();

        ParcelPerson recipient = ParcelPerson.builder()
                .email("marry.sui@gmail.com")
                .phone("+380994444444")
                .build();

        ParcelHistory parcelHistory = ParcelHistory.builder()
                .latitude(1d)
                .longitude(1d)
                .status("CREATED")
                .build();

        ParcelLocation destination = ParcelLocation.builder()
                .city("Kiyv")
                .country("Ukraine")
                .street("Shnchenka")
                .house("11/b")
                .build();

        ParcelDto parcelDto = ParcelDto.builder()
                .id("1")
                .createDate(new Date())
                .destination(destination)
                .history(List.of(parcelHistory))
                .lastUpdateDate(new Date())
                .mark("DEFAULT_PACKAGE")
                .weight(1d)
                .recipient(recipient)
                .sender(sender)
                .build();

        return List.of(parcelDto);
    }
}
