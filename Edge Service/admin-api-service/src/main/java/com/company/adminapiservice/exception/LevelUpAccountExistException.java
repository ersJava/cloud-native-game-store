package com.company.adminapiservice.exception;

public class LevelUpAccountExistException extends RuntimeException {
    public LevelUpAccountExistException(String msg){
        super(msg);
    }
}
