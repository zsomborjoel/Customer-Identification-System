package com.company.customeridentificationsystem.service;

import com.company.customeridentificationsystem.exception.UserAuthenticationException;
import com.company.customeridentificationsystem.model.response.UserCheckSystemResponse;
import com.company.customeridentificationsystem.repository.ExecutionStatusRepository;
import com.company.customeridentificationsystem.repository.UserRepository;
import com.company.customeridentificationsystem.client.UserCheckSystemClient;
import com.company.customeridentificationsystem.mapper.UserInfoDtoMapper;
import com.company.customeridentificationsystem.model.ExecutionStatus;
import com.company.customeridentificationsystem.model.User;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.company.customeridentificationsystem.constant.ExecutionStatusConstants.STARTED;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExecutionStatusRepository executionStatusRepository;

    @Autowired
    private UserCheckSystemClient userCheckSystemClient;

    @Autowired
    private UserInfoDtoMapper userInfoDtoMapper;

    @Override
    public User getUserByDocumentId(String documentId) {
        return userRepository.findByDocumentId(documentId);
    }

    /**
     * Starting authentication and adding initial value for execution process.
     * Will not duplicate execution and user data if re sent from client side.
     *
     * @param user user and its document data
     */
    @Override
    public void startAuth(User user) {
        ExecutionStatus executionStatus = new ExecutionStatus();
        executionStatus.setDocumentId(user.getDocumentId());
        executionStatus.setStatus(STARTED);
        executionStatus.setStartTime(Instant.now());

        if (executionStatusRepository.findStatusByDocumentId(user.getDocumentId()) == null) {
            executionStatusRepository.save(executionStatus);
        } else {
            executionStatusRepository.setStartTime(Instant.now(), user.getDocumentId());
        }

        if (userRepository.findByDocumentId(user.getDocumentId()) == null) {
            userRepository.save(user);
        }
    }

    /**
     * Validates user data by 4T check.
     *
     * @param user entity which will be mapped and validated
     * @return after 4T check everything is valid
     */
    @Override
    public boolean isValidUser(User user) {
        ResponseEntity<UserCheckSystemResponse> responseEntity = userCheckSystemClient.getUserCheckSystemResponse(userInfoDtoMapper.apply(user));
        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            throw new UserAuthenticationException(
                    "Unsuccessful request for user info. StatusCode: "
                            .concat(responseEntity.getStatusCode().toString()));
        }

        if (BooleanUtils.isFalse(responseEntity.getBody().getIsValidPerson())) {
            throw new UserAuthenticationException("Invalid User data for 4T check");
        }

        return true;
    }

}
