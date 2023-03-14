package com.guavapay.delivery.exception;

public class DifferentAddressesException extends RuntimeException{

    public DifferentAddressesException(String message) {
        super(String.format(message));
    }

}
