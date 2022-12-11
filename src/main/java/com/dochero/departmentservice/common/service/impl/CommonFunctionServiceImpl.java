package com.dochero.departmentservice.common.service.impl;

import com.dochero.departmentservice.common.service.CommonFunctionService;
import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.document.repository.DocumentRepository;
import com.dochero.departmentservice.exception.FolderException;
import com.dochero.departmentservice.folder.entity.Folder;
import com.dochero.departmentservice.folder.repository.FolderRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CommonFunctionServiceImpl implements CommonFunctionService {
    private final FolderRepository folderRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public CommonFunctionServiceImpl(FolderRepository folderRepository, DocumentRepository documentRepository) {
        this.folderRepository = folderRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public List<Folder> getFoldersByParentFolderIdAndDepartment(String parentFolderId, String departmentId) {
        Folder folder = folderRepository.findByIdAndDepartmentId(parentFolderId, departmentId)
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));
        return folder.getSubFolders();
    }

    @Override
    public List<Document> getDocumentsByParentFolderId(String parentFolderId) {
        // A document should be in a folder
        if (StringUtils.isBlank(parentFolderId)) {
            return Collections.emptyList();
        }
        return documentRepository.findByReferenceFolderId(parentFolderId);
    }

    @Override
    public Folder getRootFolderByDepartmentId(String departmentId) {
        return folderRepository.findByDepartmentIdAndIsRootTrue(departmentId)
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));
    }
}
