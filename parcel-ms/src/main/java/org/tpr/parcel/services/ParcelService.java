package org.tpr.parcel.services;

import org.tpr.parcel.modals.Parcel;
import org.tpr.parcel.modals.dtos.CreateParcelDto;
import org.tpr.parcel.modals.enums.ParcelStatus;

import java.util.List;

public interface ParcelService {

    List<Parcel> getAll();

    Parcel getById(String id);

    Parcel createParcel(CreateParcelDto createParcelDto);

    Parcel updateParcelStatus(ParcelStatus parcelStatus, String id);

    Parcel deleteParcel(String id);

    List<Parcel> getAllParcelsByRecipientEmailOrPhone(String email, String phone);
}
