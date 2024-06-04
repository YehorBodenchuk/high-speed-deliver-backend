package org.tpr.parcel.controller.exception;

import lombok.Getter;
import org.tpr.parcel.controller.enums.ParcelError;

@Getter
public class ParcelNotFoundException extends RuntimeException {

    private final String message;

    public ParcelNotFoundException(String id) {
        super();
        message = ParcelError.NOT_FOUND.formMessage(id);
    }
}
