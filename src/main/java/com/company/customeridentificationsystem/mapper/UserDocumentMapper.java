package com.company.customeridentificationsystem.mapper;

import com.company.customeridentificationsystem.exception.FileUploadException;
import com.company.customeridentificationsystem.model.dao.UserDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.function.BiFunction;

@Component
public class UserDocumentMapper implements BiFunction<String, MultipartFile, UserDocument> {

    @Override
    public UserDocument apply(String documentId, MultipartFile file) {
        UserDocument userDocument = new UserDocument();
        try {
            userDocument.setDocumentId(documentId);
            userDocument.setDocumentName(file.getName());
            userDocument.setDocumentType(file.getContentType());
            userDocument.setData(file.getBytes());
        } catch (IOException e) {
            throw new FileUploadException("Error occurred during file conversion: ", e);
        }
        return userDocument;
    }

}
