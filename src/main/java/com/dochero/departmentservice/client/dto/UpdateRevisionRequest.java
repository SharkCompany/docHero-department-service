package com.dochero.departmentservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRevisionRequest {
    private String revisionData;
    private List<CommentDTO> comments;
}
