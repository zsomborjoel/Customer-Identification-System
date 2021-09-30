package com.company.customeridentificationsystem.testhelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.customeridentificationsystem.model.dao.User;

public class UserHelper {

    private ObjectMapper objectMapper;

    public UserHelper() {
    }

    public UserHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getUserAsJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(getUser());
    }

    public User getUser() {
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setMothersName("mothersName");
        user.setDateOfBirth(null);
        user.setPlaceOfBirth("palaceOfBirth");
        user.setDocumentId("documentId");
        user.setHashFromDocument("hashForDocument");
        user.setHashFromPicture("hashFromPicture");
        return user;
    }



}
