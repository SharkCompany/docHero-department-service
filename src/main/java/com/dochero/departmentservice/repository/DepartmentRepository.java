package com.dochero.departmentservice.repository;

import com.dochero.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    Optional<Department> findByIdAndIsDeletedFalse(String departmentId);

    List<Department> findByIsDeletedFalse();

}
