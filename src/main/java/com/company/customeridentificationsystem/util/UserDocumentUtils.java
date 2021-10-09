package com.company.customeridentificationsystem.util;

import com.company.customeridentificationsystem.exception.FileUploadException;
import com.company.customeridentificationsystem.model.response.PictureValiditySystemResponse;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public final class UserDocumentUtils {

    private UserDocumentUtils() {
        throw new IllegalStateException("Util class");
    }

    public static boolean isNotMatching(PictureValiditySystemResponse pictureValiditySystemResponse) {
        return pictureValiditySystemResponse.getMatchingPicturePercentage() < 80;
    }

    public static void haveRequiredFileFormats(MultipartFile[] files) {
        boolean foundFormat = Arrays.stream(files)
                .allMatch(f -> MediaType.IMAGE_JPEG_VALUE.equals(f.getContentType()));
        if (!foundFormat) {
            throw new FileUploadException("Invalid file format were uploaded");
        }
    }

    public static void haveFilesInRequest(MultipartFile[] files) {
        if (files.length == 0) {
            throw new FileUploadException("No file were uploaded");
        }
    }

}
