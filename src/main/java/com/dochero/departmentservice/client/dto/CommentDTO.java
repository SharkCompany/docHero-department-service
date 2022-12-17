package com.dochero.departmentservice.client.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDTO {
    @NotBlank
    private String content;
    @NotBlank
    private String createdBy;
}
