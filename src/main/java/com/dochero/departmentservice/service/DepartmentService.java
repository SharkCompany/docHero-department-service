package com.dochero.departmentservice.service;

import com.dochero.departmentservice.dto.request.RequestCreateDepartment;
import com.dochero.departmentservice.dto.response.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(RequestCreateDepartment department);

//    DepartmentResponse updateDepartment();

    //    DepartmentResponse deleteDepartment();
//    DepartmentResponse getDepartmentById(String departmentId);

//    DepartmentResponse getAllDepartments();
}
