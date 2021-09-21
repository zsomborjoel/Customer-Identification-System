package com.company.customeridentificationsystem.service;

import com.company.customeridentificationsystem.model.User;

public interface UserService {

    User getUserByDocumentId(String documentId);
    void startAuth(User user);
    boolean isValidUser(User user);

}
