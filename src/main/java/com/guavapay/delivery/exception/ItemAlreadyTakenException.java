package com.guavapay.delivery.exception;

public class ItemAlreadyTakenException extends RuntimeException{

    public ItemAlreadyTakenException(String message) {
        super(String.format(message));
    }

}
