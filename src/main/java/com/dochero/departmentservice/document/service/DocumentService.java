package com.dochero.departmentservice.document.service;

import com.dochero.departmentservice.dto.request.CreateDocumentRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentDetailRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentTitleRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;

public interface DocumentService {

    DepartmentResponse createDocument(CreateDocumentRequest request, String credentials);

    DepartmentResponse updateDocumentTitle(String documentId , UpdateDocumentTitleRequest request, String credentials);

    DepartmentResponse updateDocumentDetail(String documentId, UpdateDocumentDetailRequest request, String credentials);

    DepartmentResponse getDocumentDetail(String documentId);

    DepartmentResponse deleteDocument(String documentId);

    DepartmentResponse getAllDocuments(Integer page, Integer record, String sortBy, String sortOrder, String searchField, String searchValue, String credentials);
}
