package com.company.customeridentificationsystem.exception;


public class FileUploadTimeoutException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileUploadTimeoutException(String message, Exception e) {
        super(message, e);
    }

}
