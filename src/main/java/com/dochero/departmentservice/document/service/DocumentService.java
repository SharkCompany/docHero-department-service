package com.dochero.departmentservice.document.service;

import com.dochero.departmentservice.dto.request.CreateDocumentRequest;
import com.dochero.departmentservice.dto.request.CreateFolderRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentRequest;
import com.dochero.departmentservice.dto.request.UpdateFolderRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;

public interface DocumentService {

    DepartmentResponse createDocument(CreateDocumentRequest request);
//
    DepartmentResponse updateDocument(String documentId , UpdateDocumentRequest request);
//
    DepartmentResponse deleteDocument(String documentId);

}
