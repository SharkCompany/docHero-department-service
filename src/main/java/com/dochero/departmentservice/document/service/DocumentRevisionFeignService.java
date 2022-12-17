package com.dochero.departmentservice.document.service;

import com.dochero.departmentservice.client.dto.DocumentRevision;
import com.dochero.departmentservice.client.dto.UpdateRevisionRequest;

import java.util.List;

public interface DocumentRevisionFeignService {
    DocumentRevision createDocumentRevision(String documentId, UpdateRevisionRequest request);

    List<DocumentRevision> getDocumentRevisionByDocId(String documentId);

    DocumentRevision createBlankRevision(String documentId);


}
