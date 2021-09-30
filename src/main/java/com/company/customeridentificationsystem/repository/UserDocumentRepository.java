package com.company.customeridentificationsystem.repository;

import com.company.customeridentificationsystem.model.dao.UserDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocumentRepository extends CrudRepository<UserDocument, Integer> {

}
