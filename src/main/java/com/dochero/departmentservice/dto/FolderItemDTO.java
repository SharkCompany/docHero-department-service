package com.dochero.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolderItemDTO {
    private String folderId;
    private String folderName;
    private String departmentId;
    private List<ItemDTO> listItems;
}
