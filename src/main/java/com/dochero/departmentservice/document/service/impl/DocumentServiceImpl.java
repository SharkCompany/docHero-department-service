package com.dochero.departmentservice.document.service.impl;

import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.department.entity.Department;
import com.dochero.departmentservice.document.repository.DocumentRepository;
import com.dochero.departmentservice.document.service.DocumentService;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.exception.DepartmentException;
import com.dochero.departmentservice.exception.FolderException;
import com.dochero.departmentservice.folder.entity.Folder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
}
