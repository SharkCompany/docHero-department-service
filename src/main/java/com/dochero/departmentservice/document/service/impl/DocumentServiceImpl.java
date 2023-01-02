package com.dochero.departmentservice.document.service.impl;

import com.dochero.departmentservice.client.DocumentRevisionClient;
import com.dochero.departmentservice.client.dto.UpdateRevisionRequest;
import com.dochero.departmentservice.client.dto.ValidateTokenResponse;
import com.dochero.departmentservice.common.service.impl.CommonFunctionServiceImpl;
import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.document.entity.DocumentType;
import com.dochero.departmentservice.document.repository.DocumentRepository;
import com.dochero.departmentservice.document.repository.DocumentTypeRepository;
import com.dochero.departmentservice.client.service.DocumentRevisionFeignService;
import com.dochero.departmentservice.document.service.DocumentService;
import com.dochero.departmentservice.client.dto.DocumentRevision;
import com.dochero.departmentservice.dto.DocumentCreateDTO;
import com.dochero.departmentservice.dto.DocumentDTO;
import com.dochero.departmentservice.dto.FolderItemsDTO;
import com.dochero.departmentservice.dto.request.CreateDocumentRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentDetailRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentTitleRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.exception.DocumentException;
import com.dochero.departmentservice.exception.FolderException;
import com.dochero.departmentservice.folder.entity.Folder;
import com.dochero.departmentservice.folder.repository.FolderRepository;
import com.dochero.departmentservice.utils.DocumentMapperUtils;
import com.dochero.departmentservice.utils.FolderItemMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;
    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentRevisionClient documentRevisionClient;

    private final DocumentRevisionFeignService documentRevisionFeignService;

    private final CommonFunctionServiceImpl commonFunctionService;

    private final ObjectMapper objectMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, FolderRepository folderRepository, DocumentTypeRepository documentTypeRepository, DocumentRevisionClient documentRevisionClient, DocumentRevisionFeignService documentRevisionFeignService, CommonFunctionServiceImpl commonFunctionService, ObjectMapper objectMapper) {
        this.documentRepository = documentRepository;
        this.folderRepository = folderRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.documentRevisionClient = documentRevisionClient;
        this.documentRevisionFeignService = documentRevisionFeignService;
        this.commonFunctionService = commonFunctionService;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public DepartmentResponse createDocument(CreateDocumentRequest request, String credentials) {
        Folder folder = folderRepository.findById(request.getFolderId())
                .orElseThrow(() -> new DocumentException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        ValidateTokenResponse userInfo = commonFunctionService
                .checkUserIsValidAndReturnUserInfo(folder.getDepartmentId(), credentials);

        DocumentType documentType = documentTypeRepository.findByExtensionName(request.getExtension())
                .orElseThrow(() -> new DocumentException(AppMessage.DOCUMENT_TYPE_NOT_FOUND_MESSAGE));

        List<Document> documents = folder.getDocuments();
        if (isDocumentTitleExist(request.getTitle(), documentType.getExtensionName(), documents)) {
            throw new DocumentException(AppMessage.DOCUMENT_TITLE_EXIST_MESSAGE);
        }

        Document document = Document.builder()
                .documentTitle(request.getTitle())
                .documentTypeId(documentType.getId())
                .referenceFolderId(folder.getId())
                .referenceDepartmentId(folder.getDepartmentId())
                .createdBy(userInfo.getUserId())
                .updatedBy(userInfo.getUserId())
                .build();
        Document saveDocument = documentRepository.save(document);

        DocumentRevision blankRevision = documentRevisionClient.createBlankRevision(saveDocument.getId());
        saveDocument.setRevisions(List.of(blankRevision));

        DocumentCreateDTO documentDTO = DocumentMapperUtils.mapDocumentToDocumentCreateDTO(document);
        return new DepartmentResponse(documentDTO, "Create document successfully");
    }



    @Override
    @Transactional
    public DepartmentResponse updateDocumentTitle(String documentId, UpdateDocumentTitleRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentException(AppMessage.DOCUMENT_NOT_FOUND_MESSAGE));
        Folder folder = folderRepository.findById(request.getFolderId())
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        // if request title or request folder is different from current document
        if (!request.getTitle().equals(document.getDocumentTitle())
                || !request.getFolderId().equals(document.getReferenceFolderId())) {
            String extensionName = document.getDocumentType().getExtensionName();
           if (isDocumentTitleExist(request.getTitle(), extensionName, folder.getDocuments())) {
               throw new DocumentException(AppMessage.DOCUMENT_TITLE_EXIST_MESSAGE);
           }

            document.setDocumentTitle(request.getTitle());
            document.setReferenceFolderId(request.getFolderId());
            //Todo: who made the changes
        }
        Document saveDocument = documentRepository.save(document);
        FolderItemsDTO folderItemsDTO = FolderItemMapperUtil.mapDocumentToFolderItemsDTO(saveDocument);
        return new DepartmentResponse(folderItemsDTO, "Update document successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse updateDocumentDetail(String documentId, UpdateDocumentDetailRequest request) {
        // Update document general information
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentException(AppMessage.DOCUMENT_NOT_FOUND_MESSAGE));
        Folder folder = folderRepository.findById(document.getReferenceFolderId())
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        if (!document.getDocumentTitle().equals(request.getTitle())) {
            if (isDocumentTitleExist(request.getTitle(), document.getDocumentType().getExtensionName(), folder.getDocuments())) {
                throw new DocumentException(AppMessage.DOCUMENT_TITLE_EXIST_MESSAGE);
            }
            document.setDocumentTitle(request.getTitle());
        }

        // Update document revision
        List<DocumentRevision> documentRevisions = documentRevisionFeignService.getDocumentRevisionByDocId(documentId);
        if (request.getIsContentChanged()) {
            UpdateRevisionRequest updateRevisionRequest = objectMapper.convertValue(request, UpdateRevisionRequest.class);
            DocumentRevision revisionForExistedDocument = documentRevisionFeignService.createDocumentRevision(documentId, updateRevisionRequest);
            documentRevisions.add(0, revisionForExistedDocument);
        }
        //Todo: Who made the changes?
        Document savedDocument = documentRepository.save(document);
        savedDocument.setRevisions(documentRevisions);

        DocumentDTO documentDTO = DocumentMapperUtils.mapDocumentToDocumentDTO(document);
        return new DepartmentResponse(documentDTO, "Update document detail successfully");
    }

    @Override
    public DepartmentResponse getDocumentDetail(String documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentException(AppMessage.DOCUMENT_NOT_FOUND_MESSAGE));
        List<DocumentRevision> documentRevisions = documentRevisionFeignService.getDocumentRevisionByDocId(documentId);
        if (documentRevisions.isEmpty()) {
            DocumentRevision blankRevision = documentRevisionFeignService.createBlankRevision(documentId);
            documentRevisions.add(blankRevision);
        }
        document.setRevisions(documentRevisions);
        DocumentDTO documentDTO = DocumentMapperUtils.mapDocumentToDocumentDTO(document);
        return new DepartmentResponse(documentDTO, "Get document detail successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse deleteDocument(String documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentException(AppMessage.DOCUMENT_NOT_FOUND_MESSAGE));
        document.setDeleted(true);
        document.setDeletedAt(Timestamp.from(Instant.now()));
        //Todo: who made the changes
        return new DepartmentResponse(null, "Delete document successfully");
    }

    private boolean isDocumentTitleExist(String title, String extension, List<Document> documents) {
        return documents.stream().anyMatch(document ->
                document.getDocumentTitle().equals(title)
                        && document.getDocumentType().getExtensionName().equalsIgnoreCase(extension));
    }
}
