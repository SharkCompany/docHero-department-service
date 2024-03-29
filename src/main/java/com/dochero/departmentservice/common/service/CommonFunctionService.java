package com.dochero.departmentservice.common.service;

import com.dochero.departmentservice.client.dto.ValidateTokenResponse;
import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.dto.UserDTO;
import com.dochero.departmentservice.folder.entity.Folder;

import java.util.List;
import java.util.Map;

public interface CommonFunctionService {
    List<Folder> getFoldersByParentFolderIdAndDepartment(String parentFolderId, String departmentId);
    List<Document> getDocumentsByParentFolderId(String parentFolderId);
    Folder getRootFolderByDepartmentId(String departmentId);
    ValidateTokenResponse checkUserIsValidAndReturnUserInfo(String departmentId, String token);
}
