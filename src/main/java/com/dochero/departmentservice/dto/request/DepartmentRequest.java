package com.dochero.departmentservice.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class DepartmentRequest {
    // not blank means not null and trim lengthh > 0
    @NotBlank(message = "Department name could not be blank")
    String name;

    @NotBlank(message = "Description could not be blank")
    String description;
}
