package com.dochero.departmentservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String id;

    private String revisionReferenceId;

    private String content;

    private Timestamp createdAt;

    private String createdBy;
}
