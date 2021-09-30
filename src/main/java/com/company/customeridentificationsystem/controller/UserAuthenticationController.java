package com.company.customeridentificationsystem.controller;

import com.company.customeridentificationsystem.model.dao.User;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication")
@Slf4j
@Controller
@RequestMapping("api/public")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private UserService userService;
    private ExecutionStatusService executionStatusService;

    @Operation(summary = "Start user auth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authentication started successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "500", description = "Unexpected exception occurred",
                    content = @Content)
    })
    @PostMapping("/auth")
    public ResponseEntity<?> startUserAuth(@RequestBody User user) {
        log.debug("Entering startUserAuth endpoint");

        try {
            userService.startAuth(user);
            return ResponseEntity.accepted().build();
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
    public ResponseEntity<?> getStatus(@RequestBody User user) {
        log.debug("Status been requested");
        return ResponseEntity.ok(executionStatusService.getExecutionStatus(user.getDocumentId()));
    }

}
