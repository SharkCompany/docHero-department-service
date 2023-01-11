package com.dochero.departmentservice.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;


@Getter
@Setter
@SuperBuilder
public class HistoryFileItemDTO extends HomePageDocumentDTO {
    private Timestamp viewedAt;
}
