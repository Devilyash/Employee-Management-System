package com.ems.employeems.exceptionHandling;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String msg) {
        super(msg);
    }
}
