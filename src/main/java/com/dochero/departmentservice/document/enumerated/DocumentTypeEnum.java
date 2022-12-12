package com.dochero.departmentservice.document.enumerated;

import lombok.Getter;

@Getter
public enum DocumentTypeEnum {
    DOCX("docx"),
    EXCEL("xls");

    private final String value;

    DocumentTypeEnum(String value) {
        this.value = value;
    }
}
