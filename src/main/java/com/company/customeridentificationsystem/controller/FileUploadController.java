package com.company.customeridentificationsystem.controller;

import com.company.customeridentificationsystem.exception.FileUploadException;
import com.company.customeridentificationsystem.model.User;
import com.company.customeridentificationsystem.service.UserDocumentService;
import com.company.customeridentificationsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.company.customeridentificationsystem.util.UserDocumentUtils.haveRequiredFileFormats;

@Slf4j
@Controller
@RequestMapping("/document")
public class FileUploadController {

    @Autowired
    private UserDocumentService userDocumentService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Upload document for related document id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document upload was successful",
                    content = { @Content(mediaType = "multipart/data-form",
                            schema = @Schema(implementation = MultipartFile[].class)) }),
            @ApiResponse(responseCode = "500", description = "Unexpected exception occurred"),
            @ApiResponse(responseCode = "400", description = "Invalid file upload based on validations")
    })
    @PostMapping("/uploadFiles/{documentId}")
    public ResponseEntity<?> uploadMultipleFiles(@PathVariable("documentId") String documentId, @RequestParam("files") MultipartFile[] files) {
        log.debug("Entering uploadMultipleFiles endpoint");
        if (files.length == 0) {
            return ResponseEntity.badRequest().body("No files were uploaded");
        }

        if (!haveRequiredFileFormats(files)) {
            return ResponseEntity.badRequest().body("Files are not in required jpeg format");
        }

        try {
            User user = userService.getUserByDocumentId(documentId);
            if (user == null) {
                return ResponseEntity.badRequest().body("User auth not yet started for this document");
            }

            userDocumentService.uploadDocuments(documentId, files);
            log.info("File upload was success for {}", documentId);

            if (!userDocumentService.isValidDocuments(files)) {
                return ResponseEntity.badRequest().body("Files are not valid by third party service");
            }

            if (!userService.isValidUser(user)) {
                return ResponseEntity.badRequest().body("The inserted user details are not correct");
            }

            return ResponseEntity.accepted().body("Files uploaded");
        } catch (FileUploadException e) {
            log.error("Unsuccessful files upload: ", e);
            return ResponseEntity.badRequest().body(e);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

}
