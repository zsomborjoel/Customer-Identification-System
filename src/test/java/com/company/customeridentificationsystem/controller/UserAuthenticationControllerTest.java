package com.company.customeridentificationsystem.controller;

import com.company.customeridentificationsystem.model.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.customeridentificationsystem.service.ExecutionStatusService;
import com.company.customeridentificationsystem.testhelper.UserHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.company.customeridentificationsystem.constant.ExecutionStatusConstants.STARTED;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserAuthenticationControllerTest {

    private MockMvc mockMvc;

    private UserHelper userHelper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ExecutionStatusService executionStatusService;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        objectMapper = new ObjectMapper();
        userHelper = new UserHelper(objectMapper);

        SecurityProperties securityProperties = Mockito.mock(SecurityProperties.class);
        Map<String, String> users = new HashMap<>();
        users.put("userOne", "passOne");
        when(securityProperties.getTestUsers()).thenReturn(users);
    }

    @WithMockUser(value = "userOne", password = "passOne", roles = "USER")
    @Test
    public void shouldReturnExecutionStatus() throws Exception {

        when(executionStatusService.getExecutionStatus(any())).thenReturn(STARTED);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/status")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(STARTED, result.getResponse().getContentAsString());
    }

}
