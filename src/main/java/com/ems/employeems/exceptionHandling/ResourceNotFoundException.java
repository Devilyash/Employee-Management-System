package com.ems.employeems.exceptionHandling;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
