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
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));

        List<Folder> result;
        if (!StringUtils.isBlank(parentFolderId)) {
            Folder folder = folderRepository.findById(parentFolderId)
                    .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));
            result = folder.getSubFolders();
        } else {
            result = getFoldersByParentFolderIdAndDepartment(parentFolderId, department.getId());
        }

        return new DepartmentResponse(result, "Get folders successfully");
    }

    @Override
    public DepartmentResponse createFolder(CreateFolderRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
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
    public DepartmentResponse updateFolder(String folderId, UpdateFolderRequest request) {
        // If folder exists
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        // if folder name and folder parent id is not  the same as the exists folder
        if (!StringUtils.equals(folder.getFolderName(), request.getFolderName()) ||
                !StringUtils.equals(folder.getParentFolderId(), request.getParentFolderId())) {

            List<Folder> relatedFolders = getFoldersByParentFolderIdAndDepartment(request.getParentFolderId(), folder.getDepartmentId());
            if (isFolderNameExists(request.getFolderName(), relatedFolders)) {
                throw new FolderException(AppMessage.FOLDER_NAME_EXISTS_MESSAGE);
            }

            folder.setFolderName(request.getFolderName());
            if (!StringUtils.isBlank(request.getParentFolderId())) {
                folder.setParentFolderId(request.getParentFolderId());
            } else {
                // null means this folder is parent folder
                folder.setParentFolderId(null);
            }
        }
        Folder updatedFolder = folderRepository.save(folder);
        return new DepartmentResponse(updatedFolder, "Update folder successfully");
    }

    @Override
    public DepartmentResponse deleteFolder(String folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));
        List<Folder> allSubFolders = Lists.newArrayList(folder);
        findFolderRecursive(folder, allSubFolders);
        allSubFolders.forEach(currentFolder -> {
                    currentFolder.setDeletedAt(Timestamp.from(Instant.now()));
                    currentFolder.setDeleted(true);
                }
        );
        folderRepository.saveAll(allSubFolders);
        return new DepartmentResponse(null, "Delete folder successfully");
    }

    private List<Folder> getFoldersByParentFolderIdAndDepartment(String parentFolderId, String departmentId) {
        Specification<Folder> specs = FolderSearchSpecification.getSearchSpec("departmentId", SearchOperation.EQUAL, departmentId);
        specs = Objects.requireNonNull(specs).and(FolderSearchSpecification.getSearchSpec("isDeleted", SearchOperation.EQUAL, false));

        // check if request has parent folder id then add specifcation to get parent folder id
        if (StringUtils.isNotBlank(parentFolderId)) {
            Folder parentFolder = folderRepository.findById(parentFolderId)
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

    private void findFolderRecursive(Folder folder, List<Folder> accumulator) {
        // find folder's children recursively and add to accumulator
        List<Folder> subFolders = folder.getSubFolders();
        if (subFolders != null && !subFolders.isEmpty()) {
            accumulator.addAll(subFolders);
            subFolders.forEach(currentFolder -> findFolderRecursive(currentFolder, accumulator));
        }
    }
}
