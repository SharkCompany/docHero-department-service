package com.dochero.departmentservice.service.impl;

import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.constant.SearchOperation;
import com.dochero.departmentservice.dto.request.CreateFolderRequest;
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
    public DepartmentResponse getFoldersTreeByDepartmentId(String departmentId) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(departmentId)
                .orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));

        Specification<Folder> specs = FolderSearchSpecification.getSearchSpec("departmentId", SearchOperation.EQUAL, department.getId());
        specs = Objects.requireNonNull(specs).and(FolderSearchSpecification.getSearchSpec("isDeleted", SearchOperation.EQUAL, false));

        List<Folder> result = folderRepository.findAll(specs);
        return new DepartmentResponse(result, "Get folders successfully");
    }

    @Override
    public DepartmentResponse getFoldersInSameParentFolderId(String parentFolderId, String departmentId) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(departmentId)
                .orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));

        Specification<Folder> specs = FolderSearchSpecification.getSearchSpec("departmentId", SearchOperation.EQUAL, department.getId());
        specs = Objects.requireNonNull(specs).and(FolderSearchSpecification.getSearchSpec("isDeleted", SearchOperation.EQUAL, false));

        // check if request has parent folder id then add specifcation to get parent folder id
        if (StringUtils.isNotBlank(parentFolderId)) {
            specs = specs.and(FolderSearchSpecification.getSearchSpec("parentFolderId", SearchOperation.EQUAL, parentFolderId));
        } else {
            specs = specs.and(FolderSearchSpecification.getSearchSpec("parentFolderId", SearchOperation.IS_NULL, null));
        }

        List<Folder> result = folderRepository.findAll(specs);
        return new DepartmentResponse(result, "Get folders successfully");
    }

    @Override
    public DepartmentResponse createFolder(CreateFolderRequest request) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));

        //if parent folder id is null or blank, then create root folder
        if (StringUtils.isBlank(request.getParentFolderId())) {
            Folder folder = Folder.builder()
                    .department(department)
                    .folderName(request.getFolderName())
                    .build();
            Folder savedFolder = folderRepository.save(folder);
            return new DepartmentResponse(savedFolder, "Create folder successfully");
        }

        Folder parentFolder = folderRepository.findByIdAndIsDeletedFalse(request.getParentFolderId())
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));
        boolean folderNameExists = isFolderNameExists(request.getFolderName(), parentFolder.getSubFolders());

        if (folderNameExists) {
            throw new FolderException(AppMessage.FOLDER_NAME_EXISTS_MESSAGE);
        }

        Folder folder = Folder.builder()
                .department(department)
                .folderName(request.getFolderName())
                .parentFolder(parentFolder)
                .build();
        Folder savedFolder = folderRepository.save(folder);
        return new DepartmentResponse(savedFolder, "Create folder successfully");
    }

    private List<Folder> getFoldersByParentFolderIdAndDepartment(String folderParentId, Department department) {
        Specification<Folder> specs = FolderSearchSpecification.getSearchSpec("departmentId", SearchOperation.EQUAL, department.getId());
        specs = Objects.requireNonNull(specs).and(FolderSearchSpecification.getSearchSpec("isDeleted", SearchOperation.EQUAL, false));

        // check if request has parent folder id then add specifcation to get parent folder id
        if (StringUtils.isNotBlank(folderParentId)) {
            specs = specs.and(FolderSearchSpecification.getSearchSpec("parentFolderId", SearchOperation.EQUAL, folderParentId));
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
