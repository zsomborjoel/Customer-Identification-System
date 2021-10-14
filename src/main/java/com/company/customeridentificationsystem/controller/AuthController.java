package com.company.customeridentificationsystem.controller;

import com.company.customeridentificationsystem.mapper.UserViewMapper;
import com.company.customeridentificationsystem.model.dao.User;
import com.company.customeridentificationsystem.model.dto.AuthUserRequest;
import com.company.customeridentificationsystem.model.dto.StatusRequest;
import com.company.customeridentificationsystem.model.dto.UserView;
import com.company.customeridentificationsystem.security.JwtTokenUtil;
import com.company.customeridentificationsystem.service.ExecutionStatusService;
import com.company.customeridentificationsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "Authentication")
@Slf4j
@Controller
@RequestMapping("api/public")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ExecutionStatusService executionStatusService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserViewMapper userViewMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody @Valid AuthUserRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
                    .body(userViewMapper.apply(user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Start user auth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authentication started successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserView.class)) }),
            @ApiResponse(responseCode = "500", description = "Unexpected exception occurred")
    })
    @PostMapping("auth")
    public UserView startUserAuth(@RequestBody @Valid AuthUserRequest request) {
        return userService.startAuth(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthUserRequest authUserRequest) {
        log.debug("Entering register endpoint");

        try {
            //userService.startAuth(createUserRequest);
            return ResponseEntity.accepted()
                    .header(HttpHeaders.AUTHORIZATION)
                    .build();
        } catch (Exception e) {
            log.error("Exception occurred in user auth: ", e);
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @Operation(summary = "Get current status of the authentication process")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authentication status returned successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "500", description = "Unexpected exception occurred")
    })
    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestBody @Valid StatusRequest statusRequest) {
        log.debug("Status been requested");
        return ResponseEntity.ok(executionStatusService.getExecutionStatus(statusRequest.getUserDocumentId()));
    }

}
