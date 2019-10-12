package com.company.adminapiservice.exception;

public class InvoiceNotFoundException extends RuntimeException {

    public InvoiceNotFoundException(String msg){
        super(msg);
    }
}
