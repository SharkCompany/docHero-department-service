package com.dochero.departmentservice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL)
    private Set<Folder> subFolders;
    private boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "department_reference_id", referencedColumnName = "id")
    private Department department;

    @PrePersist
    private void initData() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
        this.isDeleted = false;
    }

    @PreUpdate
    private void updatedTime() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
