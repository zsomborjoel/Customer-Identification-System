package com.company.customeridentificationsystem.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Set;

@Data
public class AuthUserRequest {

    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String mothersName;
    @NotBlank
    private Instant dateOfBirth;
    @NotBlank
    private String placeOfBirth;
    @NotBlank
    private String documentId;
    @NotBlank
    private String hashFromDocument;
    @NotBlank
    private String hashFromPicture;
    @NotBlank
    private String password;
    @NotBlank
    private String rePassword;
    private Set<String> authorities;




}
