package com.dochero.departmentservice.utils;

import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.dto.FolderItemDTO;
import com.dochero.departmentservice.dto.ItemDTO;
import com.dochero.departmentservice.dto.UserDTO;
import com.dochero.departmentservice.folder.entity.Folder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FolderItemMapperUtil {
    private FolderItemMapperUtil() {}

    public static ItemDTO mapFolderToItemsDTO(Folder folder, Map<String, UserDTO> userDTOMap) {
        return ItemDTO.builder()
                .itemId(folder.getId())
                .itemTitle(folder.getFolderName())
                .isFolder(true)
                .parentFolderId(folder.getParentFolderId())
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .createdBy(userDTOMap.getOrDefault(folder.getCreatedBy(), null))
                .updatedBy(userDTOMap.getOrDefault(folder.getUpdatedBy(), null))
                .build();
    }

    public static ItemDTO mapDocumentToItemsDTO(Document document, Map<String, UserDTO> userDTOMap) {
        return ItemDTO.builder()
                .itemId(document.getId())
                .itemTitle(document.getDocumentTitle())
                .isFolder(false)
                .parentFolderId(document.getReferenceFolderId())
                .extension(document.getDocumentType().getExtensionName())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .createdBy(userDTOMap.getOrDefault(document.getCreatedBy(), null))
                .updatedBy(userDTOMap.getOrDefault(document.getUpdatedBy(), null))
                .build();
    }

    public static List<ItemDTO> mapToItemDTO(List<Folder> folders, List<Document> documents, Map<String, UserDTO> mapUserDTOs) {
        List<ItemDTO> items = new ArrayList<>();
        folders.forEach(folder -> items.add(mapFolderToItemsDTO(folder, mapUserDTOs)));
        documents.forEach(document -> items.add(mapDocumentToItemsDTO(document, mapUserDTOs)));
        return items;
    }

    public static FolderItemDTO mapToFolderItemDTO(List<ItemDTO> items, Folder folder) {
        return FolderItemDTO.builder()
                .folderId(folder.getId())
                .folderName(folder.getFolderName())
                .departmentId(folder.getDepartmentId())
                .listItems(items)
                .build();
    }


}
