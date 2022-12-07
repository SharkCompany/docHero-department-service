package com.dochero.departmentservice.service;

import com.dochero.departmentservice.dto.request.CreateFolderRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;

public interface FolderService {

    //Include search
    DepartmentResponse getFoldersTreeByDepartmentId(String departmentId);

    DepartmentResponse getFoldersInSameParentFolderId(String parentFolderId, String departmentId);

    DepartmentResponse createFolder(CreateFolderRequest request);

//    DepartmentResponse updateFolder();

//    DepartmentResponse deleteFolder();

//    DepartmentResponse getAbsolutePath(String name, boolean isFolder);

}
