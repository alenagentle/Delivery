package com.guavapay.delivery.exception;

public class FieldAlreadyTakenException extends RuntimeException {

    public FieldAlreadyTakenException(String value, String field) {
        super(String.format("%s '%s' is already taken", value, field));
    }
}
