package com.company.retailapiservice.exception;

public class InvoiceNotFoundException extends RuntimeException{

    public InvoiceNotFoundException(String msg) {
        super(msg);
    }
}
