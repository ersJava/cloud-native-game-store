package com.company.adminapiservice.exception;

public class OrderProcessFailException extends RuntimeException {
    public OrderProcessFailException(String msg){
        super(msg);
    }
}
