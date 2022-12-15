package com.dochero.departmentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDocumentRequest {
    @NotBlank(message = "Document title is required")
    private String title;
    @NotBlank(message = "Extension is required")
    private String extension;
    @NotBlank(message = "Document folder is required")
    private String folderId;
}
