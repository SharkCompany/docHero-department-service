package com.dochero.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DepartmentInDocumentDTO {
    private String id;
    private String departmentName;
    private String description;
    private Timestamp updatedAt;
    private Timestamp createdAt;
}
