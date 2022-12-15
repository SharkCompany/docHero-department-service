package com.dochero.departmentservice.utils;

import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.dto.FolderItemsDTO;
import com.dochero.departmentservice.folder.entity.Folder;

import java.util.ArrayList;
import java.util.List;

public class FolderItemMapperUtil {
    private FolderItemMapperUtil() {}

    public static FolderItemsDTO mapFolderToFolderItemsDTO(Folder folder) {
        return FolderItemsDTO.builder()
                .itemId(folder.getId())
                .itemTitle(folder.getFolderName())
                .isFolder(true)
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .createdBy(folder.getCreatedBy())
                .updatedBy(folder.getUpdatedBy())
                .build();
    }

    public static FolderItemsDTO mapDocumentToFolderItemsDTO(Document document) {
        return FolderItemsDTO.builder()
                .itemId(document.getId())
                .itemTitle(document.getDocumentTitle())
                .isFolder(false)
                .extension(document.getDocumentType().getExtensionName())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .createdBy(document.getCreatedBy())
                .updatedBy(document.getUpdatedBy())
                .build();
    }

    public static List<FolderItemsDTO> mapToFolderItemDTO(List<Folder> folders, List<Document> documents) {
        List<FolderItemsDTO> items = new ArrayList<>();
        folders.forEach(folder -> items.add(mapFolderToFolderItemsDTO(folder)));
        documents.forEach(document -> items.add(mapDocumentToFolderItemsDTO(document)));
        return items;
    }
}
