package com.company.customeridentificationsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentRequest {

    private MultipartFile documentPicture;
    private MultipartFile cameraPicture;

}
