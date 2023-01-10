package com.dochero.departmentservice.dto;

import com.dochero.departmentservice.folder.entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HomePageItemDTO {
    private String itemId;
    private String itemTitle;
    private Boolean isFolder;
    private FolderDTO folder;
    private DepartmentDTO department;
    private String extension;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UserDTO createdBy;
    private UserDTO updatedBy;
}
