package com.company.customeridentificationsystem.mapper;

import com.company.customeridentificationsystem.model.dao.User;
import com.company.customeridentificationsystem.model.dto.UserView;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

@Component
public class UserViewMapper implements Function<User, UserView> {

    @Override
    public UserView apply(User user) {
        return UserView.builder()
                .id(String.valueOf(user.getId()))
                .fullName(user.getFirstName() + " " + user.getLastName())
                .username(user.getUsername())
                .build();
    }

}
