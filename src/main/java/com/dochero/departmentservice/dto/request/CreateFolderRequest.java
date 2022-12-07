package com.dochero.departmentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFolderRequest {
    @NotBlank(message = "Folder name could not be blanked")
    private String folderName;

    private String parentFolderId;

    @NotBlank(message = "Department id could not be blanked")
    private String departmentId;
}
