package org.tpr.parcel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tpr.parcel.controller.dto.UpdateParcelDto;
import org.tpr.parcel.controller.enums.ParcelError;
import org.tpr.parcel.controller.exception.ParcelAlreadyDeletedException;
import org.tpr.parcel.controller.exception.ParcelNotFoundException;
import org.tpr.parcel.modal.Parcel;
import org.tpr.parcel.modal.ParcelHistory;
import org.tpr.parcel.modal.PersonInfo;
import org.tpr.parcel.controller.dto.CreateParcelDto;
import org.tpr.parcel.modal.enums.ParcelStatus;
import org.tpr.parcel.repositorie.ParcelRepository;
import org.tpr.parcel.service.ParcelService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;

    @Override
    public List<Parcel> getAll() {
        log.info("Getting all parcels from database");
        return parcelRepository.findAll();
    }

    @Override
    public Parcel getById(String id) {
        Parcel parcel = parcelRepository.findById(id).orElseThrow(() -> {
            log.error(ParcelError.NOT_FOUND.formMessage(id));
            return new ParcelNotFoundException(id);
        });

        log.info(String.format("Getting parcel by id: %s", id));
        return parcel;
    }

    @Override
    public Parcel createParcel(CreateParcelDto createParcelDto) {
        log.info(String.format("Creating parcel to transit to: %s", createParcelDto.getDestination().getCountry()));

        PersonInfo sender = PersonInfo.builder()
                .email(createParcelDto.getSenderEmail())
                .phone(createParcelDto.getSenderPhone())
                .build();

        PersonInfo recipient = PersonInfo.builder()
                .email(createParcelDto.getRecipientEmail())
                .phone(createParcelDto.getRecipientPhone())
                .build();

        ParcelHistory history = ParcelHistory.builder()
                .status(ParcelStatus.CREATED)
                .build();

        Parcel parcel = Parcel.builder()
                .mark(createParcelDto.getMark())
                .createDate(new Date())
                .sender(sender)
                .status(ParcelStatus.CREATED)
                .recipient(recipient)
                .weight(createParcelDto.getWeight())
                .postIndex(createParcelDto.getPostIndex())
                .destination(createParcelDto.getDestination())
                .history(List.of(history))
                .build();

        return parcelRepository.save(parcel);
    }

    @Override
    public Parcel updateParcelStatus(ParcelStatus parcelStatus, String id) {
        Parcel parcel = getById(id);
        parcel.setStatus(parcelStatus);
        return parcelRepository.save(parcel);
    }

    @Override
    public Parcel update(UpdateParcelDto request, String id) {
        log.info(String.format("Updating parcel with id: %s", id));
        Parcel parcel = getById(id);

        ParcelHistory history = ParcelHistory.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .status(request.getStatus())
                .build();

        parcel.getHistory().add(history);
        parcel.setStatus(request.getStatus());

        return parcelRepository.save(parcel);
    }

    @Override
    public Parcel deleteParcel(String id) {
        Parcel parcel = getById(id);

        if (Objects.nonNull(parcel.getArchiveDate())) {
            log.error(ParcelError.ALREADY_DELETED.formMessage(id));
            throw new ParcelAlreadyDeletedException(id);
        }

        parcel.setArchiveDate(new Date());

        return parcelRepository.save(parcel);
    }

    @Override
    public List<Parcel> getAllParcelsByRecipientEmailOrPhone(String email, String phone) {
        log.info(String.format("Loading all parcels for user with email: %s and phone: %s", email, phone));
        return parcelRepository.findByRecipientEmailOrPhone(email, phone);
    }
}
