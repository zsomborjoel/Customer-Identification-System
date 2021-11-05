package com.company.customeridentificationsystem.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data @AllArgsConstructor @NoArgsConstructor
public class Role implements GrantedAuthority {

    public static final String USER_ADMIN = "USER_ADMIN";
    public static final String STATUS_ADMIN = "STATUS_ADMIN";

    private String authority;

}
