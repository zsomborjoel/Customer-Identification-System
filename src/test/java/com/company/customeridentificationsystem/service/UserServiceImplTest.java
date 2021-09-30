package com.company.customeridentificationsystem.service;

import com.company.customeridentificationsystem.model.response.UserCheckSystemResponse;
import com.company.customeridentificationsystem.client.UserCheckSystemClient;
import com.company.customeridentificationsystem.mapper.UserInfoDtoMapper;
import com.company.customeridentificationsystem.model.dao.User;
import com.company.customeridentificationsystem.repository.ExecutionStatusRepository;
import com.company.customeridentificationsystem.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExecutionStatusRepository executionStatusRepository;

    @Mock
    private UserCheckSystemClient userCheckSystemClient;

    @Mock
    private UserInfoDtoMapper userInfoDtoMapper;

    @Test
    public void shouldValidateUser() {
        UserCheckSystemResponse userCheckSystemResponse = new UserCheckSystemResponse();
        userCheckSystemResponse.setIsValidPerson(true);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<UserCheckSystemResponse> responseEntity = new ResponseEntity<>(
                userCheckSystemResponse,
                header,
                HttpStatus.OK
        );

        when(userCheckSystemClient.getUserCheckSystemResponse(any())).thenReturn(responseEntity);

        assertTrue(userService.isValidUser(new User()));
    }




}
