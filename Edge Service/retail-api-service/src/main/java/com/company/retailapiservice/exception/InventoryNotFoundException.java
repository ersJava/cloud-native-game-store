package com.company.retailapiservice.exception;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(String msg) {
        super(msg);
    }
}
