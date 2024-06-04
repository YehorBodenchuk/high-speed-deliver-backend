package org.tpr.parcel.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.tpr.parcel.modal.enums.ParcelStatus;

@Getter
@Setter
public class UpdateParcelDto {

    private ParcelStatus status;

    private Double longitude;

    private Double latitude;
}
