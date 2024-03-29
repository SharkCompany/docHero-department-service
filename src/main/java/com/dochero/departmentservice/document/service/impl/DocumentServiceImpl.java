package com.dochero.departmentservice.document.service.impl;

import com.dochero.departmentservice.client.DocumentRevisionClient;
import com.dochero.departmentservice.client.dto.*;
import com.dochero.departmentservice.client.service.AccountClientService;
import com.dochero.departmentservice.client.service.AuthenticationClientService;
import com.dochero.departmentservice.client.service.DocumentRevisionFeignService;
import com.dochero.departmentservice.common.service.CommonFunctionService;
import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.constant.SearchOperation;
import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.document.entity.DocumentType;
import com.dochero.departmentservice.document.repository.DocumentRepository;
import com.dochero.departmentservice.document.repository.DocumentTypeRepository;
import com.dochero.departmentservice.document.service.DocumentService;
import com.dochero.departmentservice.dto.*;
import com.dochero.departmentservice.dto.request.CreateDocumentRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentDetailRequest;
import com.dochero.departmentservice.dto.request.UpdateDocumentTitleRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.dto.response.PaginationResponse;
import com.dochero.departmentservice.exception.DocumentException;
import com.dochero.departmentservice.exception.FolderException;
import com.dochero.departmentservice.folder.entity.Folder;
import com.dochero.departmentservice.folder.repository.FolderRepository;
import com.dochero.departmentservice.search.DocumentSpecification;
import com.dochero.departmentservice.utils.DocumentMapperUtils;
import com.dochero.departmentservice.utils.QueryParamUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;
    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentRevisionClient documentRevisionClient;

    private final DocumentRevisionFeignService documentRevisionFeignService;

    private final CommonFunctionService commonFunctionService;

    private final AccountClientService accountClientService;

    private final AuthenticationClientService authenticationClientService;

    private final ObjectMapper objectMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, FolderRepository folderRepository, DocumentTypeRepository documentTypeRepository, DocumentRevisionClient documentRevisionClient, DocumentRevisionFeignService documentRevisionFeignService, CommonFunctionService commonFunctionService, AccountClientService accountClientService, AuthenticationClientService authenticationClientService, ObjectMapper objectMapper) {
        this.documentRepository = documentRepository;
        this.folderRepository = folderRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.documentRevisionClient = documentRevisionClient;
        this.documentRevisionFeignService = documentRevisionFeignService;
        this.commonFunctionService = commonFunctionService;
        this.accountClientService = accountClientService;
        this.authenticationClientService = authenticationClientService;
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

        DocumentBasicDTO documentDTO = DocumentMapperUtils.mapDocumentToDocumentBasicDTO(document);
        return new DepartmentResponse(documentDTO, "Create document successfully");
    }



    @Override
    @Transactional
    public DepartmentResponse updateDocumentTitle(String documentId, UpdateDocumentTitleRequest request, String credentials) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentException(AppMessage.DOCUMENT_NOT_FOUND_MESSAGE));
        Folder folder = folderRepository.findById(request.getFolderId())
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        ValidateTokenResponse userInfo = commonFunctionService
                .checkUserIsValidAndReturnUserInfo(document.getReferenceDepartmentId(), credentials);

        // if request title or request folder is different from current document
        if (!request.getTitle().equals(document.getDocumentTitle())
                || !request.getFolderId().equals(document.getReferenceFolderId())) {
            String extensionName = document.getDocumentType().getExtensionName();
           if (isDocumentTitleExist(request.getTitle(), extensionName, folder.getDocuments())) {
               throw new DocumentException(AppMessage.DOCUMENT_TITLE_EXIST_MESSAGE);
           }
            document.setDocumentTitle(request.getTitle());
            document.setReferenceFolderId(request.getFolderId());
            document.setUpdatedBy(userInfo.getUserId());
            document = documentRepository.save(document);
        }
        DocumentBasicDTO documentBasicDTO = DocumentMapperUtils.mapDocumentToDocumentBasicDTO(document);
        return new DepartmentResponse(documentBasicDTO, "Update document successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse updateDocumentDetail(String documentId, UpdateDocumentDetailRequest request, String credentials) {
        // Update document general information
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentException(AppMessage.DOCUMENT_NOT_FOUND_MESSAGE));
        Folder folder = folderRepository.findById(document.getReferenceFolderId())
                .orElseThrow(() -> new FolderException(AppMessage.FOLDER_NOT_FOUND_MESSAGE));

        ValidateTokenResponse userInfo = commonFunctionService
                .checkUserIsValidAndReturnUserInfo(folder.getDepartmentId(), credentials);

        // Update document revision
        List<DocumentRevision> documentRevisions = documentRevisionFeignService.getDocumentRevisionByDocId(documentId);
        if (request.getIsContentChanged()) {
            UpdateRevisionRequest updateRevisionRequest = objectMapper.convertValue(request, UpdateRevisionRequest.class);
            DocumentRevision revisionForExistedDocument = documentRevisionFeignService.createDocumentRevision(documentId, updateRevisionRequest);
            documentRevisions.add(0, revisionForExistedDocument);

            if (!document.getDocumentTitle().equals(request.getTitle())) {
                if (isDocumentTitleExist(request.getTitle(), document.getDocumentType().getExtensionName(), folder.getDocuments())) {
                    throw new DocumentException(AppMessage.DOCUMENT_TITLE_EXIST_MESSAGE);
                }
                document.setDocumentTitle(request.getTitle());
            }
            document.setUpdatedBy(userInfo.getUserId());
            document = documentRepository.save(document);
            document.setRevisions(documentRevisions);
        }

        //This return data may not use because FE will call getDetail again after update!
        DocumentBasicDTO documentBasicDTO = DocumentMapperUtils.mapDocumentToDocumentBasicDTO(document);
        return new DepartmentResponse(documentBasicDTO, "Update document detail successfully");
    }

    @Override
    public DepartmentResponse getDocumentDetail(String documentId, String credentials) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentException(AppMessage.DOCUMENT_NOT_FOUND_MESSAGE));

        ValidateTokenResponse userInfo = commonFunctionService
                .checkUserIsValidAndReturnUserInfo(document.getReferenceDepartmentId(), credentials);

        List<DocumentRevision> documentRevisions = documentRevisionFeignService.getDocumentRevisionByDocId(documentId);
        if (documentRevisions.isEmpty()) {
            DocumentRevision blankRevision = documentRevisionFeignService.createBlankRevision(documentId);
            documentRevisions.add(blankRevision);
        }
        document.setRevisions(documentRevisions);

        Map<String, UserDTO> mapUserDTOs = accountClientService.getAllUserDTOMap();
        DocumentDTO documentDTO = DocumentMapperUtils.mapDocumentToDocumentDTO(document, mapUserDTOs);

        // Update viewed file history
        accountClientService.updateFileHistory(userInfo.getUserId(), AccountFileHistoryUpdateRequest.builder()
                .documentId(documentId)
                .build());

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

    @Override
    public DepartmentResponse getAllDocuments(Integer page, Integer record, String sortBy, String sortOrder, String searchField, String searchValue, String credentials) {
        authenticationClientService.validateToken(credentials);

        Map<String, UserDTO> userDTOMap = accountClientService.getAllUserDTOMap();

        if (StringUtils.isBlank(sortBy)) {
            sortBy = "createdAt";
            sortOrder = Sort.Direction.DESC.name();
        }
        Sort sort = QueryParamUtil.getSortOrder(sortBy, sortOrder);

        Pageable pageable = QueryParamUtil.getPaginationOrder(page, record, sort);

        Specification specs = null;
        Page result;
        if (StringUtils.isNotBlank(searchField) && StringUtils.isNotBlank(searchValue)) {
            if ("documentTitle".equalsIgnoreCase(searchField)) {
                specs = DocumentSpecification.getSearchSpec("documentTitle", SearchOperation.LIKE, searchValue);
            } else if ("departmentId".equalsIgnoreCase(searchField)) {
                specs = DocumentSpecification.getSearchSpec("referenceDepartmentId", SearchOperation.EQUAL, searchValue);
            }
        }

        if (specs == null) {
            result = documentRepository.findAll(pageable);
        } else {
            result = documentRepository.findAll(specs, pageable);
        }

        List<Document> records = result.toList();
        long totalItem = result.getTotalElements();
        List<HomePageDocumentDTO> homePageDocumentDTOS = DocumentMapperUtils.mapListDocumentToListHomePageItemDTO(records, userDTOMap);

        PaginationResponse<HomePageDocumentDTO> paginationResponse
                = new PaginationResponse<>(pageable.getPageNumber(), pageable.getPageSize(), records.size(), (int)totalItem, homePageDocumentDTOS);

        return new DepartmentResponse(paginationResponse, "Get all documents successfully");
    }

    @Override
    public DepartmentResponse getDocumentsRecentView(String userId) {
        Map<String, UserDTO> userDTOMap = accountClientService.getAllUserDTOMap();

        List<AccountFileHistoryDTO> accountFileHistory = accountClientService.getAccountFileHistory(userId);
        List<String> documentIdList = accountFileHistory.stream().map(AccountFileHistoryDTO::getDocumentId).collect(Collectors.toList());

        Map<String, Timestamp> documentIdToViewedAtMap = accountFileHistory.stream()
                .collect(Collectors.toMap(AccountFileHistoryDTO::getDocumentId, AccountFileHistoryDTO::getViewedAt));

        List<Document> documents = documentRepository.findAllById(documentIdList);
        List<HistoryFileItemDTO> historyFileItemDTOS = DocumentMapperUtils
                .mapListDocumentToListHistoryFileItemDTO(documents, userDTOMap, documentIdToViewedAtMap);

        return new DepartmentResponse(historyFileItemDTOS, "Get documents by document ids successfully");
    }


    private boolean isDocumentTitleExist(String title, String extension, List<Document> documents) {
        return documents.stream().anyMatch(document ->
                document.getDocumentTitle().equals(title)
                        && document.getDocumentType().getExtensionName().equalsIgnoreCase(extension));
    }
}
