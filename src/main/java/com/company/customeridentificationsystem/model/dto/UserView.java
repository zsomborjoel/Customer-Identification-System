package com.company.customeridentificationsystem.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserView {

    private String id;
    private String username;
    private String fullName;

}
