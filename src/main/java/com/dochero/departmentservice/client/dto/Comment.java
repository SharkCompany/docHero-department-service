package com.dochero.departmentservice.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("content")
    private String content;
    @JsonProperty("createdAt")
    private Timestamp createdAt;
}
