package com.company.customeridentificationsystem.mapper;

import com.company.customeridentificationsystem.model.dto.UserInfoRequest;
import com.company.customeridentificationsystem.model.dao.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserInfoRequestMapper implements Function<User, UserInfoRequest> {

    @Override
    public UserInfoRequest apply(User user) {
        return UserInfoRequest.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .mothersName(user.getMothersName())
                .placeOfBirt(user.getPlaceOfBirth())
                .build();
    }

}
