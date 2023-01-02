package com.dochero.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FolderInDocumentDTO {

    private String id;

    private String folderName;

    private String createdBy;

    private String updatedBy;

    private boolean isRoot;
}
