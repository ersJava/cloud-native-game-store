package com.company.adminapiservice.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String msg){
        super(msg);
    }
}
