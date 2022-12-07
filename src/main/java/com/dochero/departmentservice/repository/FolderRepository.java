package com.dochero.departmentservice.repository;

import com.dochero.departmentservice.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, String>, JpaSpecificationExecutor<Folder> {
    Optional<Folder> findByIdAndIsDeletedFalse(String folderId);

}
