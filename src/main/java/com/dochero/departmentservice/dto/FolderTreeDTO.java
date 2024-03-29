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
public class FolderTreeDTO {
    private String key;
    private String title;
    private Boolean isRoot;
    private List<FolderTreeDTO> children;
}
