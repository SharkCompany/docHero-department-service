package com.dochero.departmentservice.utils;

import com.dochero.departmentservice.client.dto.DepartmentDTO;
import com.dochero.departmentservice.department.entity.Department;
import com.dochero.departmentservice.folder.entity.Folder;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapperUtils {

    public static DepartmentDTO convertToDepartmentDTO(Department department) {
        Folder rootFolder = department.getRootFolder().get(0);

        return DepartmentDTO.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .createdAt(department.getCreatedAt())
                .rootFolderId(rootFolder != null ? rootFolder.getId() : null)
                .updatedAt(department.getUpdatedAt())
                .build();
    }

    public static List<DepartmentDTO> convertToDepartmentDTOList(List<Department> departments) {
        return departments.stream()
                .map(DepartmentMapperUtils::convertToDepartmentDTO)
                .collect(Collectors.toList());
    }
}
