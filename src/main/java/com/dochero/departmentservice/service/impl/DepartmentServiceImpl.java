package com.dochero.departmentservice.service.impl;

import com.dochero.departmentservice.dto.request.DepartmentRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.entity.Department;
import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.exception.DepartmentException;
import com.dochero.departmentservice.repository.DepartmentRepository;
import com.dochero.departmentservice.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = Department.builder()
                .departmentName(request.getName())
                .description(request.getDescription())
                .build();
        Department savedDepartment = departmentRepository.save(department);
        return new DepartmentResponse(savedDepartment, "Create department successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse updateDepartment(String departmentId, DepartmentRequest request) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(departmentId).orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));
        department.setDepartmentName(request.getName());
        department.setDescription(request.getName());
        Department savedDepartment = departmentRepository.save(department);
        return new DepartmentResponse(savedDepartment, "Update department successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse deleteDepartment(String departmentId) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(departmentId).orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));
        department.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        department.setDeleted(true);
        departmentRepository.save(department);
        return new DepartmentResponse(null, "Delete department successfully");

    }

    @Override
    public DepartmentResponse getDepartmentById(String departmentId) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(departmentId).orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));
        return new DepartmentResponse(department, "Get department by id successfully");
    }

    @Override
    public DepartmentResponse getAllDepartments() {
        // todo consider specification query or not
        List<Department> departments = departmentRepository.findByIsDeletedFalse();
        return new DepartmentResponse(departments, "Get department successfully");
    }
}
