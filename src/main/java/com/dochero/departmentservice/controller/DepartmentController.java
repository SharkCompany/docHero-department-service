package com.dochero.departmentservice.controller;

import com.dochero.departmentservice.dto.request.DepartmentRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.department.service.DepartmentService;
import com.dochero.departmentservice.folder.service.FolderService;
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
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentService departmentService;
    private final FolderService folderService;
    @Autowired
    public DepartmentController(DepartmentService departmentService, FolderService folderService) {
        this.departmentService = departmentService;
        this.folderService = folderService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        DepartmentResponse body = new DepartmentResponse(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(body);
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody @Valid DepartmentRequest department) {
        try {
            return ResponseEntity.ok().body(departmentService.createDepartment(department));
        } catch (Exception e) {
            LOGGER.error("Failed to create department. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<?> updateDepartment(@PathVariable("departmentId") String departmentId,
                                           @RequestBody @Valid DepartmentRequest department) {
        try {
            return ResponseEntity.ok().body(departmentService.updateDepartment(departmentId, department));
        } catch (Exception e) {
            LOGGER.error("Failed to update department. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("departmentId") String departmentId) {
        try {
            return ResponseEntity.ok().body(departmentService.deleteDepartment(departmentId));
        } catch (Exception e) {
            LOGGER.error("Failed to delete department. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getDepartmentById(@PathVariable("departmentId") String departmentId) {
        try {
            return ResponseEntity.ok().body(departmentService.getDepartmentById(departmentId));
        } catch (Exception e) {
            LOGGER.error("Failed to get department by Id. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        try {
            return ResponseEntity.ok().body(departmentService.getAllDepartments());
        } catch (Exception e) {
            LOGGER.error("Failed to get department. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }

}
