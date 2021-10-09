package com.company.customeridentificationsystem.service;

import com.company.customeridentificationsystem.model.dao.ExecutionStatus;
import com.company.customeridentificationsystem.repository.ExecutionStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutionStatusServiceImpl implements ExecutionStatusService {

    private final ExecutionStatusRepository executionStatusRepository;

    /**
     * After user authentication start the execution status will be visible.
     *
     * @param documentId document identification code
     * @return Status of the auth process
     */
    @Override
    public String getExecutionStatus(String documentId) {
        ExecutionStatus executionStatus = executionStatusRepository.findStatusByDocumentId(documentId);

        if (executionStatus.getStatus() == null) {
            throw new IllegalStateException("File upload not yet started");
        }

        return executionStatus.getStatus();
    }

}
