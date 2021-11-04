package com.company.customeridentificationsystem.model.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Authority {

    @Id
    @GeneratedValue(generator="USER_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    @JsonIgnore
    private Integer id;

    private String username;

    private String authority;
}
