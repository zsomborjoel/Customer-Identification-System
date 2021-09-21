package com.company.customeridentificationsystem.mapper;

import com.company.customeridentificationsystem.model.dto.UserDocumentDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Function;

@Component
public class UserDocumentDtoMapper implements Function<MultipartFile[], UserDocumentDto> {

    private static final String SELFIE = "selfie";
    private static final String DOCUMENT = "document";

    @Override
    public UserDocumentDto apply(MultipartFile[] files) {
        UserDocumentDto userDocumentDto = new UserDocumentDto();
        for (MultipartFile file : files) {
            if (file.getName().contains(DOCUMENT))
                userDocumentDto.setDocumentPicture(file);
            if (file.getName().contains(SELFIE))
                userDocumentDto.setCameraPicture(file);
        }
        return userDocumentDto;
    }

}
