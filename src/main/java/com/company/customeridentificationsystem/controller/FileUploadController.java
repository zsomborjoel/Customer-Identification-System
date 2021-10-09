package com.company.customeridentificationsystem.controller;

import com.company.customeridentificationsystem.exception.FileUploadException;
import com.company.customeridentificationsystem.service.UserFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.company.customeridentificationsystem.util.UserDocumentUtils.haveFilesInRequest;
import static com.company.customeridentificationsystem.util.UserDocumentUtils.haveRequiredFileFormats;

@Slf4j
@Controller
@RequestMapping("api/document")
@RequiredArgsConstructor
public class FileUploadController {

    private final UserFacadeService userFacadeService;

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
        try {
            haveFilesInRequest(files);
            haveRequiredFileFormats(files);

            userFacadeService.uploadFiles(documentId, files);

            return ResponseEntity.accepted().body("Files uploaded");
        } catch (FileUploadException e) {
            log.error("Unsuccessful file upload: ", e);
            return ResponseEntity.badRequest().body(e);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

}
