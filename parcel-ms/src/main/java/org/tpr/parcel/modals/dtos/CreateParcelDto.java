package org.tpr.parcel.modals.dtos;

import lombok.*;
import org.tpr.parcel.modals.enums.ParcelMark;
import org.tpr.parcel.modals.utils.Location;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateParcelDto {

    private Double weight;

    private String postIndex;

    private ParcelMark mark;

    private String sender;

    private String recipient;

    private Location destination;
}
