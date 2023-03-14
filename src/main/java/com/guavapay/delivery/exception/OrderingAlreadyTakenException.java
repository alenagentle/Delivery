package com.guavapay.delivery.exception;

public class OrderingAlreadyTakenException extends RuntimeException{

    public OrderingAlreadyTakenException(String message) {
        super(String.format(message));
    }

}
