package com.company.customeridentificationsystem.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeoutException;

public interface UserDocumentService {

    boolean isValidDocuments(MultipartFile[] files);
    void uploadDocuments(String documentId, MultipartFile[] files) throws TimeoutException;

}
