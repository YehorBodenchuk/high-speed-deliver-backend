package org.tpr.parcel.modals;

import lombok.Getter;
import lombok.Setter;
import org.tpr.parcel.modals.enums.ParcelStatus;

@Getter
@Setter
public class ParcelHistory {

    private Double longitude;

    private Double latitude;

    private ParcelStatus status;
}
