package com.company.customeridentificationsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {

    private String fullName;
    private String mothersName;
    private Instant dateOfBirth;
    private String placeOfBirt;

}
