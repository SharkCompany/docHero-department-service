package com.dochero.departmentservice.document.repository;

import com.dochero.departmentservice.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    List<Document> findByReferenceFolderId(String parentFolderId);
}
