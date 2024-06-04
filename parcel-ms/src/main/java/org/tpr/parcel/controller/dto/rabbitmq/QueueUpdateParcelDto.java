package org.tpr.parcel.controller.dto.rabbitmq;

import lombok.*;
import org.tpr.parcel.modal.enums.ParcelStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueUpdateParcelDto {

    private String senderEmail;

    private String recipientEmail;

    private ParcelStatus status;

    private Double latitude;

    private Double longitude;
}
