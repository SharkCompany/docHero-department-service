package com.dochero.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocumentCreateDTO {
    private String id;
    private String documentTitle;
    private String folderId;
    private String documentTypeId;
    private String departmentId;
    private String createdBy;
}
