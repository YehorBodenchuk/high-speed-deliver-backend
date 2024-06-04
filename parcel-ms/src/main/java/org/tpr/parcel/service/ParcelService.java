package org.tpr.parcel.service;

import org.tpr.parcel.controller.dto.UpdateParcelDto;
import org.tpr.parcel.modal.Parcel;
import org.tpr.parcel.controller.dto.CreateParcelDto;
import org.tpr.parcel.modal.enums.ParcelStatus;

import java.util.List;

public interface ParcelService {

    List<Parcel> getAll();

    Parcel getById(String id);

    Parcel createParcel(CreateParcelDto createParcelDto);

    Parcel updateParcelStatus(ParcelStatus parcelStatus, String id);

    Parcel update(UpdateParcelDto request, String id);

    Parcel deleteParcel(String id);

    List<Parcel> getAllParcelsByRecipientEmailOrPhone(String email, String phone);
}
