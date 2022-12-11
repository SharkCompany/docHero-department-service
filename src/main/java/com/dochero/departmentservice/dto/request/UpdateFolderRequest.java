package com.dochero.departmentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFolderRequest {
    @NotBlank(message = "Folder name could not be blanked")
    private String folderName;

    @NotBlank(message = "Parent folder id could not be blanked")
    private String parentFolderId;
}