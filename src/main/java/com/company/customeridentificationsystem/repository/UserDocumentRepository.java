package com.company.customeridentificationsystem.repository;

import com.company.customeridentificationsystem.model.dao.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, Integer> {

}
