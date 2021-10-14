package com.company.customeridentificationsystem.mapper;

import com.company.customeridentificationsystem.model.dto.UserDocumentRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.function.Function;

@Component
public class UserDocumentRequestMapper implements Function<MultipartFile[], UserDocumentRequest> {

    private static final String SELFIE = "selfie";
    private static final String DOCUMENT = "document";

    @Override
    public UserDocumentRequest apply(MultipartFile[] files) {
        UserDocumentRequest userDocumentRequest = new UserDocumentRequest();
        Arrays.stream(files).forEach(file -> {
            if (file.getName().contains(DOCUMENT))
                userDocumentRequest.setDocumentPicture(file);
            if (file.getName().contains(SELFIE))
                userDocumentRequest.setCameraPicture(file);
        });
        return userDocumentRequest;
    }

}
