package com.example.SpringProject.exception;

public class DuplicatePrimaryKeyException extends RestControllerException{
    public DuplicatePrimaryKeyException(String message) {
        super(message);
    }
}
