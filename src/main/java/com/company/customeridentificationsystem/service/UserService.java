package com.company.customeridentificationsystem.service;

import com.company.customeridentificationsystem.client.UserCheckSystemClient;
import com.company.customeridentificationsystem.mapper.UserEditMapper;
import com.company.customeridentificationsystem.mapper.UserInfoRequestMapper;
import com.company.customeridentificationsystem.mapper.UserViewMapper;
import com.company.customeridentificationsystem.model.dao.ExecutionStatus;
import com.company.customeridentificationsystem.model.dao.User;
import com.company.customeridentificationsystem.model.dto.AuthUserRequest;
import com.company.customeridentificationsystem.model.dto.UserView;
import com.company.customeridentificationsystem.model.response.UserCheckSystemResponse;
import com.company.customeridentificationsystem.repository.ExecutionStatusRepository;
import com.company.customeridentificationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.Instant;
import java.util.HashSet;

import static com.company.customeridentificationsystem.constant.ExecutionStatusConstants.STARTED;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ExecutionStatusRepository executionStatusRepository;
    private final UserCheckSystemClient userCheckSystemClient;
    private final UserInfoRequestMapper userInfoRequestMapper;
    private final UserEditMapper userEditMapper;
    private final UserViewMapper userViewMapper;

    public User getUserByDocumentId(String documentId) {
        return userRepository.findByDocumentId(documentId);
    }

    /**
     * Starting authentication and adding initial value for execution process.
     * Will not duplicate execution and user data if re sent from client side.
     *
     * @param request user and its document data
     */
    public UserView startAuth(AuthUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!request.getUsername().equals(request.getRePassword())) {
            throw new ValidationException("Passwords don't match");
        }
        if (request.getAuthorities() == null) {
            request.setAuthorities(new HashSet<>());
        }

        User user = userEditMapper.apply(request);

        saveExecutionStatus(user);

        if (userRepository.findByDocumentId(user.getDocumentId()) == null) {
            userRepository.save(user);
        }

        return userViewMapper.apply(user);
    }

    private void saveExecutionStatus(User user) {
        ExecutionStatus executionStatus = new ExecutionStatus();
        executionStatus.setDocumentId(user.getDocumentId());
        executionStatus.setStatus(STARTED);
        executionStatus.setStartTime(Instant.now());

        if (executionStatusRepository.findStatusByDocumentId(user.getDocumentId()) == null) {
            executionStatusRepository.save(executionStatus);
        } else {
            executionStatusRepository.setStartTime(Instant.now(), user.getDocumentId());
        }
    }

    /**
     * Validates user data by 4T check.
     *
     * @param user entity which will be mapped and validated
     * @return after 4T check everything is valid
     */
    public boolean isValidUser(User user) {
        ResponseEntity<UserCheckSystemResponse> responseEntity = userCheckSystemClient.getUserCheckSystemResponse(userInfoRequestMapper.apply(user));
        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            throw new ValidationException(
                    "Unsuccessful request for user info. StatusCode: "
                            .concat(responseEntity.getStatusCode().toString()));
        }

        if (BooleanUtils.isFalse(responseEntity.getBody().getIsValidPerson())) {
            throw new ValidationException("Invalid User data for 4T check");
        }

        return true;
    }

}
