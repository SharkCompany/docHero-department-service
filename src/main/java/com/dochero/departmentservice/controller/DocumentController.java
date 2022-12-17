package com.dochero.departmentservice.controller;

import com.dochero.departmentservice.document.service.DocumentService;
import com.dochero.departmentservice.dto.request.CreateDocumentRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentTitleRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> createDocument(@RequestBody @Valid CreateDocumentRequest request) {
        try {
            return ResponseEntity.ok().body(documentService.createDocument(request));
        } catch (Exception e) {
            LOGGER.error("Failed to create document. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<?> updateDocument(@PathVariable("documentId") String documentId,
                                          @RequestBody @Valid UpdateDocumentTitleRequest request) {
        try {
            return ResponseEntity.ok().body(documentService.updateDocumentTitle(documentId, request));
        } catch (Exception e) {
            LOGGER.error("Failed to update document. " + e);
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

}
