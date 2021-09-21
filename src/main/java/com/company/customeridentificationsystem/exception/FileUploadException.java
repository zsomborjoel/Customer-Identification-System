package com.company.customeridentificationsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileUploadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileUploadException(String message, Exception e) {
        super(message, e);
    }

    public FileUploadException(String message) {
        super(message);
    }
}
