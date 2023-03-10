package com.guavapay.delivery.exception;

public class UnvalidatedJwtException extends RuntimeException {

    public UnvalidatedJwtException(String message) {
        super(message);
    }
}
