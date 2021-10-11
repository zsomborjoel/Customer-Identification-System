package com.company.customeridentificationsystem.repository;

import com.company.customeridentificationsystem.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user u WHERE u.document_id = :document_id", nativeQuery = true)
    User findByDocumentId(@Param("document_id") String documentId);

    Optional<User> findByUsername(String username);

}
