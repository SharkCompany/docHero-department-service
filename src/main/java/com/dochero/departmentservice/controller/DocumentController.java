package com.dochero.departmentservice.controller;

import com.dochero.departmentservice.document.service.DocumentService;
import com.dochero.departmentservice.dto.request.CreateDocumentRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentDetailRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentTitleRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/document")
public class DocumentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        DepartmentResponse body = new DepartmentResponse(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(body);
    }

    @PostMapping
    public ResponseEntity<?> createDocument(@RequestBody @Valid CreateDocumentRequest request,
                                            @RequestHeader(name = "Authorization", required = false) String credentials) {
        try {
            return ResponseEntity.ok().body(documentService.createDocument(request, credentials));
        } catch (Exception e) {
            LOGGER.error("Failed to create document. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<?> updateDocumentTitle(@PathVariable("documentId") String documentId,
                                                 @RequestBody @Valid UpdateDocumentTitleRequest request,
                                                 @RequestHeader(value = "Authorization", required = false) String credentials) {
        try {
            return ResponseEntity.ok().body(documentService.updateDocumentTitle(documentId, request, credentials));
        } catch (Exception e) {
            LOGGER.error("Failed to update document. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @PutMapping("/{documentId}/detail")
    public ResponseEntity<?> updateDocumentDetail(@PathVariable("documentId") String documentId,
                                                 @RequestBody @Valid UpdateDocumentDetailRequest request,
                                                  @RequestHeader(name = "Authorization", required = false) String credentials) {
        try {
            return ResponseEntity.ok().body(documentService.updateDocumentDetail(documentId, request, credentials));
        } catch (Exception e) {
            LOGGER.error("Failed to update document detail. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @GetMapping("/{documentId}/detail")
    public ResponseEntity<?> getDocumentDetail(@PathVariable("documentId") String documentId,
                                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String credentials) {
        try {
            return ResponseEntity.ok().body(documentService.getDocumentDetail(documentId, credentials));
        } catch (Exception e) {
            LOGGER.error("Failed to get document detail. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable("documentId") String documentId) {
        try {
            return ResponseEntity.ok().body(documentService.deleteDocument(documentId));
        } catch (Exception e) {
            LOGGER.error("Failed to delete document. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllDocuments(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                             @RequestParam(name="record", required = false, defaultValue = "10") Integer record,
                                             @RequestParam(name = "sortBy", required = false) String sortBy,
                                             @RequestParam(name = "sortOrder", required = false) String sortOrder,
                                             @RequestParam(name = "searchField", required = false) String searchField,
                                             @RequestParam(name = "searchValue", required = false) String searchValue,
                                             @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String credentials) {
        try {
            return ResponseEntity.ok().body(documentService.getAllDocuments(page, record, sortBy, sortOrder, searchField, searchValue, credentials));
        } catch (Exception e) {
            LOGGER.error("Failed to get all document. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @GetMapping("/user/{userId}/recent-docs")
    public ResponseEntity<?> getDocumentByListIds(@PathVariable("userId") String userId) {
        try {
            return ResponseEntity.ok().body(documentService.getDocumentsRecentView(userId));
        } catch (Exception e) {
            LOGGER.error("Failed to get list documents by id. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

}
