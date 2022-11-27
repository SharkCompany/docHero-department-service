package com.dochero.departmentservice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Folder {
    @Id
    @Column(name = "folder_id")
    private String folderId;
    @Column(name = "folder_name")
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "folder_id", columnDefinition = "parent_folder_id")
    @JsonBackReference
    @Nullable
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL)
    private Set<Folder> subFolders;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "department_reference_id",
            referencedColumnName = "department_id",
            columnDefinition = "department_reference_id")
    @JsonIgnore
    private Department department;

    @PrePersist
    private void initData() {
        this.folderId = UUID.randomUUID().toString();
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    private void updatedTime() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
