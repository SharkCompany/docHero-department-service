package com.dochero.departmentservice.folder.service;

import com.dochero.departmentservice.dto.request.CreateFolderRequest;
import com.dochero.departmentservice.dto.request.UpdateFolderRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;

public interface FolderService {

    DepartmentResponse getItemsInFolder(String folderId, String credential);

    DepartmentResponse createFolder(CreateFolderRequest request, String credential);

    DepartmentResponse updateFolder(String folderId ,UpdateFolderRequest request);

    DepartmentResponse deleteFolder(String folderId);

    DepartmentResponse getFolderTreeOfDepartment(String departmentId, String excludedId);


}
