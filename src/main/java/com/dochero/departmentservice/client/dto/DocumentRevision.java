package com.dochero.departmentservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRevision {
    private String id;

    private String documentId;

    private String revisionData;

    private Timestamp createdAt;

    private List<Comment> comments;
}
