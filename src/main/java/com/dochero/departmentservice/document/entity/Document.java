package com.dochero.departmentservice.document.entity;

import com.dochero.departmentservice.client.dto.DocumentRevision;
import com.dochero.departmentservice.department.entity.Department;
import com.dochero.departmentservice.folder.entity.Folder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document")
@Where(clause = "is_deleted = false")
public class Document {
    @Id
    @Column(name = "document_id")
    private String id;

    @Column(name = "document_title")
    private String documentTitle;

    @Column(name = "document_type_id")
    private String documentTypeId;

    @Column(name = "document_folder_id")
    private String referenceFolderId;

    @Column(name = "document_department_id")
    private String referenceDepartmentId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

     @ManyToOne
     @JoinColumn(name = "document_folder_id",referencedColumnName = "folder_id", insertable = false, updatable = false )
     private Folder folder;

     @ManyToOne
     @JoinColumn(name = "document_type_id",referencedColumnName = "document_type_id", insertable = false, updatable = false )
     private DocumentType documentType;

     @ManyToOne
     @JoinColumn(name ="document_department_id", referencedColumnName = "department_id", insertable = false, updatable = false )
     private Department department;


    @Transient
    private List<DocumentRevision> revisions;

    @PrePersist
    private void initData() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
        this.isDeleted = false;
    }

    @PreUpdate
    private void updatedTime() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
