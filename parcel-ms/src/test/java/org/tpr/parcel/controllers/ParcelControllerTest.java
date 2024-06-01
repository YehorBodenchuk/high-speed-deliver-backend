package org.tpr.parcel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.tpr.parcel.controllers.exceptions.ParcelAlreadyDeletedException;
import org.tpr.parcel.controllers.exceptions.ParcelNotFoundException;
import org.tpr.parcel.modals.Parcel;
import org.tpr.parcel.modals.dtos.CreateParcelDto;
import org.tpr.parcel.modals.enums.ParcelStatus;
import org.tpr.parcel.services.impls.ParcelServiceImpl;
import org.tpr.parcel.utils.DataUtil;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
class ParcelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParcelServiceImpl parcelService;

    @Test
    @DisplayName("Test get all parcels")
    void givenThreeParcelsPersisted_whenGetAll_thenThreeParcelsReturned() throws Exception {
        List<Parcel> persistedParcels = List.of(
                DataUtil.getParcelFrancePersisted(),
                DataUtil.getParcelUSAPersisted(),
                DataUtil.getParcelJapanPersisted()
        );
        BDDMockito.given(parcelService.getAll()).willReturn(persistedParcels);

        ResultActions result = mockMvc.perform(get("/api/parcel/"));

        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Test get parcel by id")
    void givenParcelPersisted_whenGetById_thenParcelReturned() throws Exception {
        Parcel persistedParcel = DataUtil.getParcelUSAPersisted();
        BDDMockito.given(parcelService.getById(persistedParcel.getId())).willReturn(persistedParcel);

        ResultActions result = mockMvc.perform(get(String.format("/api/parcel/%s", persistedParcel.getId())));

        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(persistedParcel.getId())));
    }

    @Test
    @DisplayName("Test parcel not found by id")
    void givenParcelNotExist_whenGetById_thenParcelNotFoundErrorOccurred() throws Exception {
        BDDMockito.given(parcelService.getById(Mockito.anyString())).willThrow(ParcelNotFoundException.class);

        ResultActions result = mockMvc.perform(get("/api/parcel/1"));

        result
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test create parcel")
    void givenParcelTransient_whenCreateParcel_thenPersistedParcelReturned() throws Exception {
        Parcel persistedParcel = DataUtil.getParcelUSAPersisted();
        BDDMockito.given(parcelService.createParcel(Mockito.any(CreateParcelDto.class))).willReturn(persistedParcel);
        CreateParcelDto parcelDto = DataUtil.getParcelUSATransient();

        ResultActions result = mockMvc.perform(post("/api/parcel/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parcelDto))
        );

        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Test update parcel status by id")
    void givenParcelPersisted_whenUpdateStatus_thenUpdatedParcelReturned() throws Exception {
        Parcel persistedParcel = DataUtil.getParcelJapanPersisted();
        persistedParcel.setStatus(ParcelStatus.IN_TRANSIT);
        BDDMockito.given(parcelService.updateParcelStatus(persistedParcel.getStatus(), persistedParcel.getId())).willReturn(persistedParcel);

        ResultActions result = mockMvc.perform(put(String.format("/api/parcel/%s/%s", persistedParcel.getId(), persistedParcel.getStatus())));

        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(persistedParcel.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(persistedParcel.getStatus().toString())));
    }

    @Test
    @DisplayName("Test parcel not found when updating by id")
    void givenParcelNotExist_whenUpdateStatus_thenParcelNotFoundErrorOccurred() throws Exception {
        BDDMockito.given(parcelService.updateParcelStatus(Mockito.any(ParcelStatus.class), Mockito.anyString())).willThrow(ParcelNotFoundException.class);

        ResultActions result = mockMvc.perform(put("/api/parcel/1/IN_TRANSIT"));

        result
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test delete parcel by id")
    void givenParcelPersisted_whenDeleteById_thenDeletedParcelReturned() throws Exception {
        Parcel persistedParcel = DataUtil.getParcelUSAPersisted();
        persistedParcel.setArchiveDate(new Date());
        BDDMockito.given(parcelService.deleteParcel(persistedParcel.getId())).willReturn(persistedParcel);

        ResultActions result = mockMvc.perform(delete(String.format("/api/parcel/%s", persistedParcel.getId())));

        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(persistedParcel.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.archiveDate", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Test parcel not found when deleting by id")
    void givenParcelNoExist_whenDeleteById_thenParcelNotFoundErrorOccurred() throws Exception {
        BDDMockito.given(parcelService.deleteParcel(Mockito.anyString())).willThrow(ParcelNotFoundException.class);

        ResultActions result = mockMvc.perform(delete("/api/parcel/1"));

        result
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test parcel already deleted")
    void givenParcelAlreadyDeleted_whenDeleteById_thenParcelAlreadyDeletedErrorOccurred() throws Exception {
        BDDMockito.given(parcelService.deleteParcel(Mockito.anyString())).willThrow(ParcelAlreadyDeletedException.class);

        ResultActions result = mockMvc.perform(delete("/api/parcel/1"));

        result
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}