package com.dochero.departmentservice.department.service.impl;

import com.dochero.departmentservice.client.dto.DepartmentDTO;
import com.dochero.departmentservice.common.service.CommonFunctionService;
import com.dochero.departmentservice.constant.AdministratorConstants;
import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.dto.request.DepartmentRequest;
import com.dochero.departmentservice.dto.response.DepartmentResponse;
import com.dochero.departmentservice.department.entity.Department;
import com.dochero.departmentservice.constant.AppMessage;
import com.dochero.departmentservice.exception.DepartmentException;
import com.dochero.departmentservice.department.repository.DepartmentRepository;
import com.dochero.departmentservice.department.service.DepartmentService;
import com.dochero.departmentservice.folder.entity.Folder;
import com.dochero.departmentservice.folder.repository.FolderRepository;
import com.dochero.departmentservice.utils.DepartmentMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;
    private final FolderRepository folderRepository;
    private final CommonFunctionService commonFunctionService;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, FolderRepository folderRepository, CommonFunctionService commonFunctionService) {
        this.departmentRepository = departmentRepository;
        this.folderRepository = folderRepository;
        this.commonFunctionService = commonFunctionService;
    }

    @Override
    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (isDepartmentNameExisted(request.getName())) {
            throw new DepartmentException(AppMessage.DEPARTMENT_NAME_EXISTED);
        }

        Department department = Department.builder()
                .departmentName(request.getName())
                .description(request.getDescription())
                .build();
        Department savedDepartment = departmentRepository.save(department);

        String rootFolderName = savedDepartment.getId() + "_root";
        Folder rootFolder = Folder.builder()
                .departmentId(savedDepartment.getId())
                .folderName(rootFolderName)
                .isRoot(true)
                .createdBy(AdministratorConstants.SYSTEM_CREATION)
                .build();
        folderRepository.save(rootFolder);
        return new DepartmentResponse(savedDepartment, "Create department successfully");
    }

    // check if department name is existed ignore case
    private boolean isDepartmentNameExisted(String departmentName) {
        return departmentRepository.existsByDepartmentNameIgnoreCase(departmentName);
    }

    @Override
    @Transactional
    public DepartmentResponse updateDepartment(String departmentId, DepartmentRequest request) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));
        department.setDepartmentName(request.getName());
        department.setDescription(request.getName());
        Department savedDepartment = departmentRepository.save(department);
        return new DepartmentResponse(savedDepartment, "Update department successfully");
    }

    @Override
    @Transactional
    public DepartmentResponse deleteDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));
        department.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        department.setDeleted(true);
        departmentRepository.save(department);
        return new DepartmentResponse(null, "Delete department successfully");
    }

    private void deleteItemsBelongToDepartment(String departmentId) {
        Folder rootFolderByDepartmentId = commonFunctionService.getRootFolderByDepartmentId(departmentId);
        List<Document> documentsByParentFolderId = commonFunctionService.getDocumentsByParentFolderId(rootFolderByDepartmentId.getId());
        //soft delete all documents
        documentsByParentFolderId.forEach(document -> {
            document.setDeleted(true);
            document.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        });
        List<Folder> subFolders = rootFolderByDepartmentId.getSubFolders();

    }

    @Override
    public DepartmentResponse getDepartmentById(String departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentException(AppMessage.DEPARTMENT_NOT_FOUND_MESSAGE));
        DepartmentDTO departmentDTO = DepartmentMapperUtils.convertToDepartmentDTO(department);
        return new DepartmentResponse(departmentDTO, "Get department by id successfully");
    }

    @Override
    public DepartmentResponse getAllDepartments() {
        // todo consider specification query or not
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOS = DepartmentMapperUtils.convertToDepartmentDTOList(departments);
        return new DepartmentResponse(departmentDTOS, "Get department successfully");
    }
}
