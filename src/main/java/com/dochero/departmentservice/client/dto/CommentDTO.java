package com.dochero.departmentservice.client.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDTO {
    @NotBlank(message = "UserId could not be blank")
    private String userId;
    @NotBlank(message = "FullName could not be blank")
    private String fullName;
    @NotBlank(message = "Comment content could not be blank")
    private String content;
}
