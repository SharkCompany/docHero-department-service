package com.dochero.departmentservice.department.service;

import com.dochero.departmentservice.dto.request.DepartmentRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest department);

    DepartmentResponse updateDepartment(String departmentId, DepartmentRequest department);

    DepartmentResponse deleteDepartment(String departmentId);

    DepartmentResponse getDepartmentById(String departmentId);

    DepartmentResponse getAllDepartments();

}
