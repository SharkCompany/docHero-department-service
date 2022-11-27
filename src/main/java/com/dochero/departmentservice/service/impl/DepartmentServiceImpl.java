package com.dochero.departmentservice.service.impl;

import com.dochero.departmentservice.dto.request.RequestCreateDepartment;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public DepartmentResponse createDepartment(RequestCreateDepartment department) {
        return new DepartmentResponse("Hello", "");

    }
}
