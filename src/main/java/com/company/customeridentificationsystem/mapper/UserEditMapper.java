package com.company.customeridentificationsystem.mapper;

import com.company.customeridentificationsystem.model.dao.User;
import com.company.customeridentificationsystem.model.dto.AuthUserRequest;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper implements Function<AuthUserRequest, User> {

    @Override
    public User apply(AuthUserRequest authUserRequest) {
        return User.builder()
                .username(authUserRequest.getUsername())
                .password(authUserRequest.getPassword())
                .firstName(authUserRequest.getFirstName())
                .lastName(authUserRequest.getLastName())
                .mothersName(authUserRequest.getMothersName())
                .dateOfBirth(authUserRequest.getDateOfBirth())
                .placeOfBirth(authUserRequest.getPlaceOfBirth())
                .documentId(authUserRequest.getDocumentId())
                .hashFromDocument(authUserRequest.getHashFromDocument())
                .hashFromPicture(authUserRequest.getHashFromPicture())
                .build();
    }

}
