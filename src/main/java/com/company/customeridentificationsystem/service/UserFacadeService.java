package com.company.customeridentificationsystem.service;

import com.company.customeridentificationsystem.exception.FileUploadException;
import com.company.customeridentificationsystem.model.dao.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacadeService {

    private final UserDocumentService userDocumentService;
    private final UserService userService;

    public void uploadFiles(String documentId, MultipartFile[] files) throws TimeoutException {
        User user = userService.getUserByDocumentId(documentId);
        if (user == null) {
            throw new FileUploadException("User auth not yet started for this document");
        }

        userDocumentService.uploadDocuments(documentId, files);
        log.info("File upload was success for {}", documentId);

        if (!userDocumentService.isValidDocuments(files)) {
            throw new FileUploadException("Files are not valid by third party service");
        }

        if (!userService.isValidUser(user)) {
            throw new FileUploadException("The inserted user details are not correct");
        }
    }

}
