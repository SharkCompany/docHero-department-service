package com.dochero.departmentservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;
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
