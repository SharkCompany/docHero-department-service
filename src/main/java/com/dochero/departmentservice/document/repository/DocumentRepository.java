package com.dochero.departmentservice.document.repository;

import com.dochero.departmentservice.document.entity.Document;
import com.dochero.departmentservice.folder.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String>, JpaSpecificationExecutor<Document> {

    List<Document> findByReferenceFolderId(String parentFolderId);

    List<Document> findByReferenceDepartmentId(String departmentId);

    List<Document> findByIdIn(List<String> documentIds);
}
