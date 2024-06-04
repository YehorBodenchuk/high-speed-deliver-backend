package org.tpr.parcel.modal;

import lombok.*;
import org.tpr.parcel.modal.enums.ParcelStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParcelHistory {

    private Double longitude;

    private Double latitude;

    private ParcelStatus status;
}
