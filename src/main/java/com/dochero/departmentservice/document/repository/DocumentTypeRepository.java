package com.dochero.departmentservice.document.repository;

import com.dochero.departmentservice.document.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, String> {
    Optional<DocumentType> findByExtensionName(String extensionName);
}
