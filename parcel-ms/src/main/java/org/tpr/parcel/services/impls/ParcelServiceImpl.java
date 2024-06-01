package org.tpr.parcel.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tpr.parcel.controllers.enums.ParcelError;
import org.tpr.parcel.controllers.exceptions.ParcelAlreadyDeletedException;
import org.tpr.parcel.controllers.exceptions.ParcelNotFoundException;
import org.tpr.parcel.modals.Parcel;
import org.tpr.parcel.modals.dtos.CreateParcelDto;
import org.tpr.parcel.modals.enums.ParcelStatus;
import org.tpr.parcel.repositories.ParcelRepository;
import org.tpr.parcel.services.ParcelService;

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
        Parcel parcel = Parcel.builder()
                .mark(createParcelDto.getMark())
                .createDate(new Date())
                .sender(createParcelDto.getSender())
                .status(ParcelStatus.CREATED)
                .recipient(createParcelDto.getRecipient())
                .weight(createParcelDto.getWeight())
                .postIndex(createParcelDto.getPostIndex())
                .destination(createParcelDto.getDestination())
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
    public Parcel deleteParcel(String id) {
        Parcel parcel = getById(id);

        if (Objects.nonNull(parcel.getArchiveDate())) {
            log.error(ParcelError.ALREADY_DELETED.formMessage(id));
            throw new ParcelAlreadyDeletedException(id);
        }

        parcel.setArchiveDate(new Date());

        return parcelRepository.save(parcel);
    }
}
