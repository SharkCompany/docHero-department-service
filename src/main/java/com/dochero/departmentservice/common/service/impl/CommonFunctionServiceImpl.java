package com.dochero.departmentservice.common.service.impl;

import com.dochero.departmentservice.client.dto.ValidateTokenResponse;
import com.dochero.departmentservice.client.service.AuthenticationClientService;
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
    private final AuthenticationClientService authenticationClientService;

    @Autowired
    public CommonFunctionServiceImpl(FolderRepository folderRepository, DocumentRepository documentRepository, AuthenticationClientService authenticationClientService) {
        this.folderRepository = folderRepository;
        this.documentRepository = documentRepository;
        this.authenticationClientService = authenticationClientService;
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

    @Override
    public ValidateTokenResponse checkUserIsValidAndReturnUserInfo(String departmentId, String credentials) {
        ValidateTokenResponse validateTokenResponse = authenticationClientService.validateToken(credentials);
        boolean isUserInDepartment = validateTokenResponse.getDepartmentIDs().stream().anyMatch(departmentId::equals);
        if (!isUserInDepartment
                && !validateTokenResponse.getRoleName().equalsIgnoreCase("ADMINISTRATOR")
                && !validateTokenResponse.getRoleName().equalsIgnoreCase("MANAGER")) {
            throw new FolderException(AppMessage.USER_NOT_AUTHORIZE_MESSAGE);
        } else {
            return validateTokenResponse;
        }
    }

}
