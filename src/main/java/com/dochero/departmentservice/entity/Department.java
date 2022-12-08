package com.dochero.departmentservice.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
@Where(clause = "is_deleted = false")
public class Department {
    @Id
    @Column(name = "department_id")
    private String id;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
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
