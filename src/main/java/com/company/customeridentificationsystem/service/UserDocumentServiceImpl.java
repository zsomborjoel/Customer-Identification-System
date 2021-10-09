package com.company.customeridentificationsystem.service;

import com.company.customeridentificationsystem.client.PictureValiditySystemClient;
import com.company.customeridentificationsystem.constant.ExecutionStatusConstants;
import com.company.customeridentificationsystem.exception.FileUploadException;
import com.company.customeridentificationsystem.mapper.UserDocumentDtoMapper;
import com.company.customeridentificationsystem.mapper.UserDocumentMapper;
import com.company.customeridentificationsystem.model.dao.ExecutionStatus;
import com.company.customeridentificationsystem.model.properties.UserAuthProperties;
import com.company.customeridentificationsystem.model.response.PictureValiditySystemResponse;
import com.company.customeridentificationsystem.repository.ExecutionStatusRepository;
import com.company.customeridentificationsystem.repository.UserDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import static com.company.customeridentificationsystem.util.UserDocumentUtils.isNotMatching;

@Service
@RequiredArgsConstructor
public class UserDocumentServiceImpl implements UserDocumentService {

    private final UserDocumentRepository userDocumentRepository;
    private final UserDocumentMapper userDocumentMapper;
    private final UserDocumentDtoMapper userDocumentDtoMapper;
    private final PictureValiditySystemClient pictureValiditySystemClient;
    private final ExecutionStatusRepository executionStatusRepository;
    private final UserAuthProperties userAuthProperties;

    /**
     * Upload necessary document and follow execution statuses
     *
     * @param documentId uploaded document identification for updating current execution status
     * @param files required files for upload
     * @throws TimeoutException custom timeout after user authentication started
     */
    @Override
    public void uploadDocuments(String documentId, MultipartFile[] files) throws TimeoutException {
        ExecutionStatus executionStatus = executionStatusRepository.findStatusByDocumentId(documentId);
        if (executionStatus.getStartTime().isAfter(Instant.now().minusMillis(userAuthProperties.getTimeout()))) {
            throw new TimeoutException("File upload timed out");
        }

        executionStatusRepository.setStatus(ExecutionStatusConstants.IN_PROGRESS, documentId);

        Arrays.stream(files)
                .forEach(f -> userDocumentRepository.save(userDocumentMapper.apply(documentId, f)));
        executionStatusRepository.setStatus(ExecutionStatusConstants.DONE, documentId);
    }

    /**
     * Validation by third party if uploaded documents are matching or not
     *
     * @param files Uploaded picture from document and user selfie
     * @return if documents are matching based on third party tool
     */
    @Override
    public boolean isValidDocuments(MultipartFile[] files) {
        ResponseEntity<PictureValiditySystemResponse> responseEntity = pictureValiditySystemClient
                .getPictureValiditySystemResponse(userDocumentDtoMapper.apply(files));
        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            throw new FileUploadException(
                    "Unsuccessful request for user info. StatusCode: "
                            .concat(responseEntity.getStatusCode().toString()));
        }

        if (isNotMatching(responseEntity.getBody())) {
            throw new FileUploadException("Invalid Document data for Picture validity check");
        }

        return true;
    }

}
