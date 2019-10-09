package com.company.customerservice.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String msg) {
        super(msg);
    }

}
