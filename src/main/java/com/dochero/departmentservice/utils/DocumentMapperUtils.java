package com.dochero.departmentservice.utils;

import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.dto.DocumentCreateDTO;
import com.dochero.departmentservice.dto.DepartmentInDocumentDTO;
import com.dochero.departmentservice.dto.DocumentDTO;
import com.dochero.departmentservice.dto.FolderInDocumentDTO;

public class DocumentMapperUtils {
    public static DocumentDTO mapDocumentToDocumentDTO(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .documentTitle(document.getDocumentTitle())
                .createdBy(document.getCreatedBy())
                .updatedBy(document.getUpdatedBy())
                .deletedAt(document.getDeletedAt())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .folder(FolderInDocumentDTO.builder()
                        .id(document.getFolder().getId())
                        .folderName(document.getFolder().getFolderName())
                        .createdBy(document.getFolder().getCreatedBy())
                        .updatedBy(document.getFolder().getUpdatedBy())
                        .isRoot(document.getFolder().isRoot())
                        .build())
                .documentType(document.getDocumentType())
                .department(DepartmentInDocumentDTO.builder()
                        .id(document.getDepartment().getId())
                        .departmentName(document.getDepartment().getDepartmentName())
                        .description(document.getDepartment().getDescription())
                        .createdAt(document.getDepartment().getCreatedAt())
                        .updatedAt(document.getDepartment().getUpdatedAt())
                        .build())
                .revisions(document.getRevisions())
                .build();
    }

    //Map Document to DocumentCreateDTO
    public static DocumentCreateDTO mapDocumentToDocumentCreateDTO(Document document) {
        return DocumentCreateDTO.builder()
                .id(document.getId())
                .documentTitle(document.getDocumentTitle())
                .createdBy(document.getCreatedBy())
                .folderId(document.getReferenceFolderId())
                .documentTypeId(document.getDocumentTypeId())
                .departmentId(document.getReferenceDepartmentId())
                .build();
    }
}
