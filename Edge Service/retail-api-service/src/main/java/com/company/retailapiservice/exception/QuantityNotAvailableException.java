package com.company.retailapiservice.exception;

public class QuantityNotAvailableException extends RuntimeException {

    public QuantityNotAvailableException(String msg) {
        super(msg);
    }
}
