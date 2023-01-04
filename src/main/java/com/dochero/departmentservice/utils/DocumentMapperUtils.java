package com.dochero.departmentservice.utils;

import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.dto.*;

import java.util.Map;

public class DocumentMapperUtils {
    public static DocumentDTO mapDocumentToDocumentDTO(Document document, Map<String, UserDTO> userDTOMap) {
        return DocumentDTO.builder()
                .id(document.getId())
                .documentTitle(document.getDocumentTitle())
                .createdBy(userDTOMap.getOrDefault(document.getCreatedBy(), null))
                .updatedBy(userDTOMap.getOrDefault(document.getUpdatedBy(), null))
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
    public static DocumentBasicDTO mapDocumentToDocumentBasicDTO(Document document) {
        return DocumentBasicDTO.builder()
                .id(document.getId())
                .documentTitle(document.getDocumentTitle())
                .createdBy(document.getCreatedBy())
                .folderId(document.getReferenceFolderId())
                .documentTypeId(document.getDocumentTypeId())
                .departmentId(document.getReferenceDepartmentId())
                .build();
    }
}
