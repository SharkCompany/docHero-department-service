package com.dochero.departmentservice.dto.request;

import com.dochero.departmentservice.client.dto.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDocumentDetailRequest {
    @NotBlank(message = "Document title is required")
    private String title;
    private Boolean isContentChanged;
    private String revisionData;
    @Valid
    private List<Comment> comments;
}
