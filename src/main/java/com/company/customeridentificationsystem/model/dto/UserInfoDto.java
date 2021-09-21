package com.company.customeridentificationsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private String userName;
    private String mothersName;
    private Instant dateOfBirth;
    private String placeOfBirt;

}
