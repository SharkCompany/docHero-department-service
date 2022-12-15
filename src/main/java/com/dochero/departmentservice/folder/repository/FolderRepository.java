package com.dochero.departmentservice.folder.repository;

import com.dochero.departmentservice.folder.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, String>, JpaSpecificationExecutor<Folder> {
    Optional<Folder> findByDepartmentIdAndIsRootTrue(String departmentId);
    Optional<Folder> findByIdAndDepartmentId(String folderId, String departmentId);

}
