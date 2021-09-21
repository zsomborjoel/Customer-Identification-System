package com.company.customeridentificationsystem.client;

import com.company.customeridentificationsystem.model.dto.UserInfoDto;
import com.company.customeridentificationsystem.model.properties.UserCheckSystemProperties;
import com.company.customeridentificationsystem.model.response.UserCheckSystemResponse;
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
public class UserCheckSystemClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserCheckSystemProperties properties;

    public ResponseEntity<UserCheckSystemResponse> getUserCheckSystemResponse(UserInfoDto userInfo) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserInfoDto> request = new HttpEntity<>(userInfo, headers);
        return restTemplate.postForEntity(properties.getUrl(), request, UserCheckSystemResponse.class);
    }

}
