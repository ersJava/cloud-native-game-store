package com.company.retailapiservice.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String msg) {
        super(msg);
    }

}