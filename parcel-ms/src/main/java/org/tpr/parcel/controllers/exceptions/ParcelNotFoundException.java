package org.tpr.parcel.controllers.exceptions;

import lombok.Getter;
import org.tpr.parcel.controllers.enums.ParcelError;

@Getter
public class ParcelNotFoundException extends RuntimeException {

    private final String message;

    public ParcelNotFoundException(String id) {
        super();
        message = ParcelError.NOT_FOUND.formMessage(id);
    }
}
