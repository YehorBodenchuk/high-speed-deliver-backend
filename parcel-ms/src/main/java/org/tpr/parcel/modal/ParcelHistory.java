package org.tpr.parcel.modal;

import lombok.Getter;
import lombok.Setter;
import org.tpr.parcel.modal.enums.ParcelStatus;

@Getter
@Setter
public class ParcelHistory {

    private Double longitude;

    private Double latitude;

    private ParcelStatus status;
}
