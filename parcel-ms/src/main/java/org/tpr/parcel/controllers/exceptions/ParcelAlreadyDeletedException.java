package org.tpr.parcel.controllers.exceptions;

import lombok.Getter;
import org.tpr.parcel.controllers.enums.ParcelError;

@Getter
public class ParcelAlreadyDeletedException extends RuntimeException {

    private final String message;

    public ParcelAlreadyDeletedException(String id) {
        super();
        message = ParcelError.ALREADY_DELETED.formMessage(id);
    }
}
