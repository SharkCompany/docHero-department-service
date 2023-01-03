package com.dochero.departmentservice.dto;

import com.dochero.departmentservice.client.dto.DocumentRevision;
import com.dochero.departmentservice.department.entity.Department;
import com.dochero.departmentservice.document.entity.DocumentType;
import com.dochero.departmentservice.folder.entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocumentDTO {
    private String id;

    private String documentTitle;

    private UserDTO createdBy;

    private UserDTO updatedBy;

    private Timestamp deletedAt;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private FolderInDocumentDTO folder;

    private DocumentType documentType;

    private DepartmentInDocumentDTO department;

    private List<DocumentRevision> revisions;
}
