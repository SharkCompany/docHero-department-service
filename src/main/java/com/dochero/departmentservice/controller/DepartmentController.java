package com.dochero.departmentservice.controller;

import com.dochero.departmentservice.dto.request.RequestCreateDepartment;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService departmentService;

    public ResponseEntity createDepartment(RequestCreateDepartment department) {
        try {
            return ResponseEntity.ok().body(departmentService.createDepartment(department));
        } catch (Exception e) {
            LOGGER.error("Failed to create department. " + e);
            DepartmentResponse body = new DepartmentResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(body);
        }
    }


}
