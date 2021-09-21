package com.company.customeridentificationsystem.client;

import com.company.customeridentificationsystem.model.dto.UserDocumentDto;
import com.company.customeridentificationsystem.model.properties.PictureValiditySystemProperties;
import com.company.customeridentificationsystem.model.response.PictureValiditySystemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class PictureValiditySystemClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PictureValiditySystemProperties properties;

    public ResponseEntity<PictureValiditySystemResponse> getPictureValiditySystemResponse(UserDocumentDto userDocument) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<UserDocumentDto> request = new HttpEntity<>(userDocument, headers);
        return restTemplate.postForEntity(properties.getUrl(), request, PictureValiditySystemResponse.class);
    }

}
