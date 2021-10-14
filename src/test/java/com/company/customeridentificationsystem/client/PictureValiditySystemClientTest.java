package com.company.customeridentificationsystem.client;

import com.company.customeridentificationsystem.model.dto.UserDocumentRequest;
import com.company.customeridentificationsystem.model.properties.PictureValiditySystemProperties;
import com.company.customeridentificationsystem.model.response.PictureValiditySystemResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PictureValiditySystemClientTest{

    @InjectMocks
    private PictureValiditySystemClient pictureValiditySystemClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PictureValiditySystemProperties properties;

    @Test
    public void shouldReturnPictureValiditySystemResponse() {
        PictureValiditySystemResponse pictureValiditySystemResponse = new PictureValiditySystemResponse();
        pictureValiditySystemResponse.setMatchingPicturePercentage(80);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<UserDocumentRequest> request = new HttpEntity<>(new UserDocumentRequest(), headers);

        when(properties.getUrl()).thenReturn("http:");
        when(restTemplate.postForEntity("http:", request, PictureValiditySystemResponse.class)).thenReturn(new ResponseEntity<>(pictureValiditySystemResponse, HttpStatus.OK));
        assertEquals(HttpStatus.OK, pictureValiditySystemClient.getPictureValiditySystemResponse(new UserDocumentRequest()).getStatusCode());
    }

}
