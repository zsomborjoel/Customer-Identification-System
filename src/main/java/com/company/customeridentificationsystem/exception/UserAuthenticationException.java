package com.company.customeridentificationsystem.exception;


public class UserAuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserAuthenticationException(String message) {
        super(message);
    }

}
