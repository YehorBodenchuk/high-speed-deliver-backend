package org.tpr.parcel.controller.dto;

import lombok.*;
import org.tpr.parcel.modal.enums.ParcelMark;
import org.tpr.parcel.modal.Location;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateParcelDto {

    private Double weight;

    private String postIndex;

    private ParcelMark mark;

    private String senderEmail;

    private String senderPhone;

    private String recipientEmail;

    private String recipientPhone;

    private Location destination;
}
