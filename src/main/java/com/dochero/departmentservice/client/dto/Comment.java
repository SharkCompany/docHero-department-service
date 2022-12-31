package com.dochero.departmentservice.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @JsonProperty("userId")
    @NotBlank(message = "UserId could not be blank")
    private String userId;
    @JsonProperty("fullName")
    @NotBlank(message = "FullName could not be blank")
    private String fullName;
    @JsonProperty("content")
    @NotBlank(message = "Comment content could not be blank")
    private String content;
    @NotBlank(message = "CreatedAt could not be blank")
    @JsonProperty("createdAt")
    private String createdAt;

}
