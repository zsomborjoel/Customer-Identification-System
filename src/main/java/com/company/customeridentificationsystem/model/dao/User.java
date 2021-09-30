package com.company.customeridentificationsystem.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

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

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
