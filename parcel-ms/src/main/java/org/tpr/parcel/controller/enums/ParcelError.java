package org.tpr.parcel.controller.enums;

public enum ParcelError {
    NOT_FOUND {
        @Override
        public String formMessage(String id) {
            return String.format("Parcel with id %s not found!", id);
        }

        @Override
        public Integer getCode() {
            return 404;
        }
    },
    ALREADY_DELETED {
        @Override
        public String formMessage(String id) {
            return String.format("Parcel with id %s is already deleted!", id);
        }

        @Override
        public Integer getCode() {
            return 409;
        }
    };

    abstract public Integer getCode();

    abstract public String formMessage(String id);
}
