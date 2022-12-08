package com.dochero.departmentservice.controller;

import com.dochero.departmentservice.dto.request.CreateFolderRequest;
import com.dochero.departmentservice.dto.request.UpdateFolderRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.service.FolderService;
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
@RequestMapping("/folder")
public class FolderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FolderController.class);

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        DepartmentResponse body = new DepartmentResponse(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(body);
    }

    @PostMapping
    public ResponseEntity<?> createFolder(@RequestBody @Valid CreateFolderRequest request) {
        try {
            return ResponseEntity.ok().body(folderService.createFolder(request));
        } catch (Exception e) {
            LOGGER.error("Failed to create folder. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<?> updateFolder(@PathVariable("folderId") String folderId,
                                          @RequestBody @Valid UpdateFolderRequest request) {
        try {
            return ResponseEntity.ok().body(folderService.updateFolder(folderId, request));
        } catch (Exception e) {
            LOGGER.error("Failed to Update folder. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteFolder(@PathVariable("folderId") String folderId) {
        try {
            return ResponseEntity.ok().body(folderService.deleteFolder(folderId));
        } catch (Exception e) {
            LOGGER.error("Failed to delete folder. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }
}
