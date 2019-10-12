package com.company.adminapiservice.exception;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(String msg){
        super(msg);
    }
}
