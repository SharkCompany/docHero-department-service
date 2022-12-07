package com.dochero.departmentservice.service.impl;

import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.constant.SearchOperation;
import com.dochero.departmentservice.dto.request.CreateFolderRequest;
import com.dochero.departmentservice.dto.request.UpdateFolderRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.entity.Department;
import com.dochero.departmentservice.entity.Folder;
import com.dochero.departmentservice.exception.DepartmentException;
import com.dochero.departmentservice.exception.FolderException;
import com.dochero.departmentservice.repository.DepartmentRepository;
import com.dochero.departmentservice.repository.FolderRepository;
import com.dochero.departmentservice.search.FolderSearchSpecification;
import com.dochero.departmentservice.service.FolderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final DepartmentRepository departmentRepository;
    @Autowired
    public FolderServiceImpl(FolderRepository folderRepository, DepartmentRepository departmentRepository) {
        this.folderRepository = folderRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponse getFoldersInSameParentFolderId(String departmentId, String parentFolderId) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(departmentId)
                .orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));

        List<Folder> result;
        if (!StringUtils.isBlank(parentFolderId)) {
            Folder folder = folderRepository.findByIdAndIsDeletedFalse(parentFolderId)
                    .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));
            result = folder.getSubFolders();
        } else {
            result = getFoldersByParentFolderIdAndDepartment(parentFolderId, department.getId());
        }

        return new DepartmentResponse(result, "Get folders successfully");
    }

    @Override
    public DepartmentResponse createFolder(CreateFolderRequest request) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));

        List<Folder> relatedFolders = getFoldersByParentFolderIdAndDepartment(request.getParentFolderId(), department.getId());
        if (isFolderNameExists(request.getFolderName(), relatedFolders)) {
            throw new FolderException(AppMessage.FOLDER_NAME_EXISTS_MESSAGE);
        }

        Folder folder = Folder.builder()
                .departmentId(department.getId())
                .folderName(request.getFolderName())
                .build();
        if (!StringUtils.isBlank(request.getParentFolderId())) {
            folder.setParentFolderId(request.getParentFolderId());
        }

        Folder savedFolder = folderRepository.save(folder);
        return new DepartmentResponse(savedFolder, "Create folder successfully");
    }

    @Override
    public DepartmentResponse updateFolder(UpdateFolderRequest request) {
        return null;
    }

    private List<Folder> getFoldersByParentFolderIdAndDepartment(String parentFolderId, String departmentId) {
        Specification<Folder> specs = FolderSearchSpecification.getSearchSpec("departmentId", SearchOperation.EQUAL, departmentId);
        specs = Objects.requireNonNull(specs).and(FolderSearchSpecification.getSearchSpec("isDeleted", SearchOperation.EQUAL, false));

        // check if request has parent folder id then add specifcation to get parent folder id
        if (StringUtils.isNotBlank(parentFolderId)) {
            Folder parentFolder = folderRepository.findByIdAndIsDeletedFalse(parentFolderId)
                    .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));
            specs = specs.and(FolderSearchSpecification.getSearchSpec("parentFolderId", SearchOperation.EQUAL, parentFolder.getId()));
        } else {
            specs = specs.and(FolderSearchSpecification.getSearchSpec("parentFolderId", SearchOperation.IS_NULL, null));
        }
        return folderRepository.findAll(specs);
    }

    private boolean isFolderNameExists(String folderName, List<Folder> subFolders) {
        return subFolders.stream()
                .anyMatch(currentFolder -> currentFolder.getFolderName().equalsIgnoreCase(folderName));
    }
}
