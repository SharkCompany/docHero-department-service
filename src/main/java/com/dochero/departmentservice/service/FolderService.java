package com.dochero.departmentservice.service;

import com.dochero.departmentservice.dto.request.CreateFolderRequest;
import com.dochero.departmentservice.dto.request.UpdateFolderRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;

public interface FolderService {

    DepartmentResponse getFoldersInSameParentFolderId(String departmentId, String parentFolderId);

    DepartmentResponse createFolder(CreateFolderRequest request);

    DepartmentResponse updateFolder(UpdateFolderRequest request);

//    DepartmentResponse deleteFolder();

//    DepartmentResponse getAbsolutePath(String name, boolean isFolder);

}
