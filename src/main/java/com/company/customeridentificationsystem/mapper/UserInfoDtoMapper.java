package com.company.customeridentificationsystem.mapper;

import com.company.customeridentificationsystem.model.dto.UserInfoDto;
import com.company.customeridentificationsystem.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserInfoDtoMapper implements Function<User, UserInfoDto> {

    @Override
    public UserInfoDto apply(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserName(user.getFirstName() + " " + user.getLastName());
        userInfoDto.setDateOfBirth(user.getDateOfBirth());
        userInfoDto.setMothersName(user.getMothersName());
        userInfoDto.setPlaceOfBirt(user.getPlaceOfBirth());
        return userInfoDto;
    }

}
