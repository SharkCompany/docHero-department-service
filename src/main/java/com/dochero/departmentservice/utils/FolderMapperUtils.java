package com.dochero.departmentservice.utils;

import com.dochero.departmentservice.dto.FolderDTO;
import com.dochero.departmentservice.folder.entity.Folder;

public class FolderMapperUtils {
    //Map Folder to FolderDTO
    public static FolderDTO mapFolderToFolderDTO(Folder folder) {
        return FolderDTO.builder()
                .folderId(folder.getId())
                .folderName(folder.getFolderName())
                .departmentId(folder.getDepartment().getId())
                .build();
    }
}
