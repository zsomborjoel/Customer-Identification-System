package com.company.customeridentificationsystem.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator="USER_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    @JsonIgnore
    private Integer id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "mothers_name")
    private String mothersName;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "hash_from_document", length = 1000)
    private String hashFromDocument;

    @Column(name = "hash_from_picture", length = 1000)
    private String hashFromPicture;

    @Column(unique = true)
    private String username;

    private String password;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private boolean enabled = true;

}
