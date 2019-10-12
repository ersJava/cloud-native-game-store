package com.company.adminapiservice.exception;

public class DeleteNotAllowedException extends RuntimeException {

    public DeleteNotAllowedException(String msg){
        super(msg);
    }
}
