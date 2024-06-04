package org.tpr.parcel.modals.dtos;

import lombok.*;
import org.tpr.parcel.modals.enums.ParcelMark;
import org.tpr.parcel.modals.Location;

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
