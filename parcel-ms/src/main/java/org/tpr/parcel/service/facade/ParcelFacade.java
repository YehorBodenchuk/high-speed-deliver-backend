package org.tpr.parcel.service.facade;

import org.tpr.parcel.controller.dto.CreateParcelDto;
import org.tpr.parcel.controller.dto.UpdateParcelDto;
import org.tpr.parcel.modal.Parcel;

public interface ParcelFacade {

    // Update parcel and notify sender and recipient
    Parcel updateParcel(UpdateParcelDto request, String id);

    // Create parcel and notify sender and recipient
    Parcel createParcel(CreateParcelDto request);
}
