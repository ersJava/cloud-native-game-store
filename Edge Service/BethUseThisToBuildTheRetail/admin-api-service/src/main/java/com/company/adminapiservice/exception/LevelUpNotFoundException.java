package com.company.adminapiservice.exception;

public class LevelUpNotFoundException extends RuntimeException {

    public LevelUpNotFoundException(String msg){
        super(msg);
    }
}
