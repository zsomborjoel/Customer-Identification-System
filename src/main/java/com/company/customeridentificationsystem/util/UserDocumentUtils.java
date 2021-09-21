package com.company.customeridentificationsystem.util;

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

    public static boolean haveRequiredFileFormats(MultipartFile[] files) {
        return Arrays.stream(files)
                .allMatch(f -> MediaType.IMAGE_JPEG_VALUE.equals(f.getContentType()));
    }

}
