package com.dochero.departmentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFolderRequest {
    @NotBlank(message = "Folder name could not be blanked")
    private String folderName;

    @NotBlank(message = "Parent folder id could not be blank")
    private String parentFolderId;

}
