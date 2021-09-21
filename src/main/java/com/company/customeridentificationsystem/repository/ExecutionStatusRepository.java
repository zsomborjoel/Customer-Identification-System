package com.company.customeridentificationsystem.repository;

import com.company.customeridentificationsystem.model.ExecutionStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ExecutionStatusRepository extends CrudRepository<ExecutionStatus, Integer> {

    @Query(value = "SELECT * FROM execution_status as es WHERE es.document_id = :document_id", nativeQuery = true)
    ExecutionStatus findStatusByDocumentId(@Param("document_id") String documentId);

    @Modifying
    @Query(value = "UPDATE execution_status es SET es.status = :status WHERE es.document_id = :document_id", nativeQuery = true)
    void setStatus(@Param("status") String status, @Param("document_id") String documentId);

    @Modifying
    @Query(value = "UPDATE execution_status es SET es.start_time = :start_time WHERE es.document_id = :document_id", nativeQuery = true)
    void setStartTime(@Param("start_time") Instant startTime, @Param("document_id") String documentId);

}
