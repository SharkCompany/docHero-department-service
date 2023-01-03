package com.dochero.departmentservice.folder.service.impl;

import com.dochero.departmentservice.client.dto.ValidateTokenResponse;
import com.dochero.departmentservice.common.service.CommonFunctionService;
import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.department.repository.DepartmentRepository;
import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.document.repository.DocumentRepository;
import com.dochero.departmentservice.dto.FolderItemsDTO;
import com.dochero.departmentservice.dto.request.CreateFolderRequest;
import com.dochero.departmentservice.dto.request.UpdateFolderRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.exception.FolderException;
import com.dochero.departmentservice.folder.entity.Folder;
import com.dochero.departmentservice.folder.repository.FolderRepository;
import com.dochero.departmentservice.folder.service.FolderService;
import com.dochero.departmentservice.utils.FolderItemMapperUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final DepartmentRepository departmentRepository;
    private final CommonFunctionService commonFunctionService;
    private final DocumentRepository documentRepository;
    @Autowired
    public FolderServiceImpl(FolderRepository folderRepository, DepartmentRepository departmentRepository, CommonFunctionService commonFunctionService, DocumentRepository documentRepository) {
        this.folderRepository = folderRepository;
        this.departmentRepository = departmentRepository;
        this.commonFunctionService = commonFunctionService;
        this.documentRepository = documentRepository;
    }

    @Override
    public DepartmentResponse getItemsInFolder(String folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        List<Folder> folders = folderRepository.findByParentFolderId(folder.getId());
        List<Document> documents = documentRepository.findByReferenceFolderId(folder.getId());

        List<FolderItemsDTO> folderItemsDTOS = FolderItemMapperUtil.mapToFolderItemDTO(folders, documents);
        return new DepartmentResponse(folderItemsDTOS, "Get folders successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse createFolder(CreateFolderRequest request, String credential) {
        Folder parentFolder = folderRepository.findById(request.getParentFolderId())
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        //check if user is valid to create folder
        ValidateTokenResponse validateTokenResponse =
                commonFunctionService.checkUserIsValidAndReturnUserInfo(parentFolder.getDepartmentId(), credential);

        List<Folder> folders = folderRepository.findByParentFolderId(parentFolder.getId());
        if (isFolderNameExists(request.getFolderName(), folders)) {
            throw new FolderException(AppMessage.FOLDER_NAME_EXISTS_MESSAGE);
        }

        Folder folder = Folder.builder()
                .departmentId(parentFolder.getDepartmentId())
                .parentFolderId(parentFolder.getId())
                .folderName(request.getFolderName())
                .isRoot(false)
                .createdBy(validateTokenResponse.getUserId())
                .build();
        Folder savedFolder = folderRepository.save(folder);
        return new DepartmentResponse(savedFolder, "Create folder successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse updateFolder(String folderId, UpdateFolderRequest request) {
        // If folder exists
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        //What if folder move to folder in another department?

        if (folder.isRoot()) {
            throw new FolderException(AppMessage.FOLDER_CANNOT_DELETE_ROOT_MESSAGE);
        }

        // if folder name and folder parent id is not  the same as the exists folder
        if (!StringUtils.equals(folder.getFolderName(), request.getFolderName()) ||
                !StringUtils.equals(folder.getParentFolderId(), request.getParentFolderId())) {

            List<Folder> relatedFolders = commonFunctionService
                    .getFoldersByParentFolderIdAndDepartment(request.getParentFolderId(), folder.getDepartmentId());
            if (isFolderNameExists(request.getFolderName(), relatedFolders)) {
                throw new FolderException(AppMessage.FOLDER_NAME_EXISTS_MESSAGE);
            }

            folder.setFolderName(request.getFolderName());
            folder.setParentFolderId(request.getParentFolderId());
            //Todo: who made the change
        }
        Folder updatedFolder = folderRepository.save(folder);
        return new DepartmentResponse(updatedFolder, "Update folder successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse deleteFolder(String folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        if (folder.isRoot()) {
            throw new FolderException(AppMessage.FOLDER_CANNOT_DELETE_ROOT_MESSAGE);
        }

        // In case implement the recycle bin
        folder.setParentFolderId(null);

        List<Folder> allSubFolders = Lists.newArrayList(folder);
        List<Document> allDocuments = Lists.newArrayList();
        findFolderRecursive(folder, allSubFolders, allDocuments);
        allSubFolders.forEach(currentFolder -> {
                    currentFolder.setDeletedAt(Timestamp.from(Instant.now()));
                    currentFolder.setDeleted(true);
                    //Todo: who delete the folder
                }
        );
        allDocuments.forEach(currentDocument -> {
                    currentDocument.setDeletedAt(Timestamp.from(Instant.now()));
                    currentDocument.setDeleted(true);
                    //Todo: who delete the document
                }
        );
        folderRepository.saveAll(allSubFolders);
        return new DepartmentResponse(null, "Delete folder successfully");
    }

    private boolean isFolderNameExists(String folderName, List<Folder> subFolders) {
        return subFolders.stream()
                .anyMatch(currentFolder -> currentFolder.getFolderName().equalsIgnoreCase(folderName));
    }

    private void findFolderRecursive(Folder folder, List<Folder> folderAccumulator, List<Document> documentAccumulator) {
        List<Document> documents = folder.getDocuments();
        if (documents != null && !documents.isEmpty()) {
            documentAccumulator.addAll(documents);
        }

        // find folder's children recursively and add to accumulator
        List<Folder> subFolders = folder.getSubFolders();
        if (subFolders != null && !subFolders.isEmpty()) {
            folderAccumulator.addAll(subFolders);
            subFolders.forEach(currentFolder -> findFolderRecursive(currentFolder, folderAccumulator, documentAccumulator));
        }
    }
}
