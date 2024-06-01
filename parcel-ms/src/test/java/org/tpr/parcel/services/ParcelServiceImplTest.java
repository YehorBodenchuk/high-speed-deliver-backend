package org.tpr.parcel.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tpr.parcel.controllers.exceptions.ParcelAlreadyDeletedException;
import org.tpr.parcel.controllers.exceptions.ParcelNotFoundException;
import org.tpr.parcel.modals.Parcel;
import org.tpr.parcel.modals.enums.ParcelStatus;
import org.tpr.parcel.repositories.ParcelRepository;
import org.tpr.parcel.services.impls.ParcelServiceImpl;
import org.tpr.parcel.utils.DataUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class ParcelServiceImplTest {

    @Mock
    private ParcelRepository parcelRepository;

    @InjectMocks
    private ParcelServiceImpl parcelService;

    @Test
    @DisplayName("Test get all parcels from DB")
    void givenThreeParcels_whenGetAll_thenThreeParcelsReturned() {
        List<Parcel> persistedParcels = List.of(
                DataUtil.getParcelFrancePersisted(),
                DataUtil.getParcelUSAPersisted(),
                DataUtil.getParcelJapanPersisted()
        );
        BDDMockito.given(parcelRepository.findAll()).willReturn(persistedParcels);

        List<Parcel> obtainedParcels = parcelService.getAll();

        assertEquals(3, obtainedParcels.size());
    }

    @Test
    @DisplayName("Test get parcel by id from DB")
    void givenParcelPersisted_whenGetById_thenParcelReturned() {
        Parcel persistedParcel = DataUtil.getParcelFrancePersisted();
        BDDMockito.given(parcelRepository.findById(persistedParcel.getId())).willReturn(Optional.of(persistedParcel));

        Parcel obtainedParcel = parcelService.getById(persistedParcel.getId());

        assertEquals(persistedParcel.getId(), obtainedParcel.getId());
    }

    @Test
    @DisplayName("Test parcel not found in DB")
    void givenParcelNotExist_whenGetById_thenParcelNotFoundErrorOccurred() {
        BDDMockito.given(parcelRepository.findById(Mockito.anyString())).willThrow(ParcelNotFoundException.class);

        assertThrows(ParcelNotFoundException.class, () -> parcelService.getById("1"));
    }

    @Test
    void givenParcelTransient_whenCreate_thenParcelPersisted() {
        BDDMockito.given(parcelRepository.save(Mockito.any(Parcel.class))).willReturn(DataUtil.getParcelUSAPersisted());

        Parcel obtainedParcel = parcelService.createParcel(DataUtil.getParcelUSATransient());

        assertNotNull(obtainedParcel);
        assertNotNull(obtainedParcel.getId());
        assertEquals(ParcelStatus.CREATED, obtainedParcel.getStatus());
    }

    @Test
    @DisplayName("Test update parcel status by id in DB")
    void givenParcelPersisted_whenUpdateStatus_thenParcelWithUpdatedStatusReturned() {
        Parcel persistedParcel = DataUtil.getParcelUSAPersisted();
        BDDMockito.given(parcelRepository.findById(persistedParcel.getId())).willReturn(Optional.of(persistedParcel));
        Parcel updatedParcel = DataUtil.getParcelUSAPersisted();
        updatedParcel.setStatus(ParcelStatus.CANCELED);
        BDDMockito.given(parcelRepository.save(Mockito.any(Parcel.class))).willReturn(updatedParcel);

        Parcel obtainedParcel = parcelService.updateParcelStatus(updatedParcel.getStatus(), persistedParcel.getId());

        assertEquals(persistedParcel.getId(), obtainedParcel.getId());
        assertEquals(updatedParcel.getStatus(), obtainedParcel.getStatus());
    }

    @Test
    @DisplayName("Test parcel not found while updating status by id in DB")
    void givenParcelNotExist_whenUpdateStatus_thenParcelNotFoundErrorOccurred() {
        BDDMockito.given(parcelRepository.findById(Mockito.anyString())).willThrow(ParcelNotFoundException.class);

        assertThrows(ParcelNotFoundException.class, () -> parcelService.updateParcelStatus(ParcelStatus.CANCELED, "1"));
    }

    @Test
    @DisplayName("Test delete parcel by id from DB")
    void givenParcelPersisted_whenDeleteById_thenDeletedParcelReturned() {
        Parcel persistedParcel = DataUtil.getParcelJapanPersisted();
        BDDMockito.given(parcelRepository.findById(persistedParcel.getId())).willReturn(Optional.of(persistedParcel));
        Parcel updatedParcel = DataUtil.getParcelJapanPersisted();
        updatedParcel.setArchiveDate(new Date());
        BDDMockito.given(parcelRepository.save(Mockito.any(Parcel.class))).willReturn(updatedParcel);

        Parcel obtainedParcel = parcelService.deleteParcel(persistedParcel.getId());

        assertEquals(persistedParcel.getId(), obtainedParcel.getId());
        assertNotNull(persistedParcel.getArchiveDate());
    }

    @Test
    @DisplayName("Test parcel not found when deleting from DB")
    void givenParcelNotExist_whenDeleteById_thenParcelNotFoundErrorOccurred() {
        BDDMockito.given(parcelRepository.findById(Mockito.anyString())).willThrow(ParcelNotFoundException.class);

        assertThrows(ParcelNotFoundException.class, () -> parcelService.deleteParcel("1"));
    }

    @Test
    @DisplayName("Test parcel already deleted when deleting from DB")
    void giveParcelAlreadyDeletedPersisted_whenDeleteById_thenParcelAlreadyDeletedErrorOccurred() {
        Parcel persistedParcel = DataUtil.getParcelJapanPersisted();
        persistedParcel.setArchiveDate(new Date());
        BDDMockito.given(parcelRepository.findById(persistedParcel.getId())).willReturn(Optional.of(persistedParcel));

        assertThrows(ParcelAlreadyDeletedException.class, () -> parcelService.deleteParcel(persistedParcel.getId()));
    }
}