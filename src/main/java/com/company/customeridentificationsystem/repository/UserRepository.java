package com.company.customeridentificationsystem.repository;

import com.company.customeridentificationsystem.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM user u WHERE u.document_id = :document_id", nativeQuery = true)
    User findByDocumentId(@Param("document_id") String documentId);

}
