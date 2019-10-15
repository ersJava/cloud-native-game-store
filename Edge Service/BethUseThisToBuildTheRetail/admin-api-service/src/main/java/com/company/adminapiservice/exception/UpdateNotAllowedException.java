package com.company.adminapiservice.exception;

public class UpdateNotAllowedException extends RuntimeException {

    public UpdateNotAllowedException(String msg){
        super(msg);
    }
}
