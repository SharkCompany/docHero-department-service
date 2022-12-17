package com.dochero.departmentservice.document.service.impl;

import com.dochero.departmentservice.client.DocumentRevisionClient;
import com.dochero.departmentservice.client.dto.DocumentRevision;
import com.dochero.departmentservice.client.dto.UpdateRevisionRequest;
import com.dochero.departmentservice.document.service.DocumentRevisionFeignService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentRevisionFeignServiceImpl implements DocumentRevisionFeignService {
    private final DocumentRevisionClient documentRevisionClient;

    @Autowired
    public DocumentRevisionFeignServiceImpl(DocumentRevisionClient documentRevisionClient) {
        this.documentRevisionClient = documentRevisionClient;
    }

    @Override
    public DocumentRevision createDocumentRevision(String documentId, UpdateRevisionRequest request) {
        try {
            return documentRevisionClient.createRevisionForExistedDocument(documentId, request);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentRevision> getDocumentRevisionByDocId(String documentId) {
        try {
            return documentRevisionClient.getAllRevisionsByDocumentId(documentId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentRevision createBlankRevision(String documentId) {
        try {
            return documentRevisionClient.createBlankRevision(documentId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
