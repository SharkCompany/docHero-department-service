package com.dochero.departmentservice.document.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document_type")
public class DocumentType {

    @Id
    @Column(name = "document_type_id")
    private String id;

    @Column(name = "type_name")
    private String documentTypeName;

    @Column(name = "extension_name")
    private String extensionName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

}
