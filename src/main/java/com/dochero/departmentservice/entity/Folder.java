package com.dochero.departmentservice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Folder {
    @Id
    @Column(name = "folder_id")
    private String id;
    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "parent_folder_id")
    private String parentFolderId;

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

    @Column(name = "department_reference_id")
    private String departmentId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "department_id")
    @JsonIgnore
    private Department department;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id",referencedColumnName = "folder_id", insertable = false, updatable = false )
    @JsonBackReference
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL)
    private List<Folder> subFolders;

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
