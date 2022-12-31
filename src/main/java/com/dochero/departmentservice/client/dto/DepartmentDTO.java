package com.dochero.departmentservice.client.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    private String id;

    private String departmentName;

    private String description;

    private Timestamp createdAt;

    private String rootFolderId;

    private Timestamp updatedAt;
}
