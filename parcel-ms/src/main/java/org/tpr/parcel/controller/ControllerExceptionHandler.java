package org.tpr.parcel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tpr.parcel.controller.enums.ParcelError;
import org.tpr.parcel.controller.exception.ParcelAlreadyDeletedException;
import org.tpr.parcel.controller.exception.ParcelNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ParcelNotFoundException.class})
    public ResponseEntity<String> handleException(ParcelNotFoundException exception) {
        return ResponseEntity.status(ParcelError.NOT_FOUND.getCode()).body(exception.getMessage());
    }

    @ExceptionHandler({ParcelAlreadyDeletedException.class})
    public ResponseEntity<String> handleException(ParcelAlreadyDeletedException exception) {
        return ResponseEntity.status(ParcelError.ALREADY_DELETED.getCode()).body(exception.getMessage());
    }
}
