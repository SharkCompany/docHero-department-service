package com.dochero.departmentservice.department.repository;

import com.dochero.departmentservice.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    Boolean existsByDepartmentNameIgnoreCase(String departmentName);
}
