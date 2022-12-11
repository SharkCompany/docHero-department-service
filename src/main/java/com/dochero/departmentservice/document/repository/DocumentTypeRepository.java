package com.dochero.departmentservice.document.repository;

import com.dochero.departmentservice.document.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, String> {

}
