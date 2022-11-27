package com.dochero.departmentservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCreateDepartment {
    String name;
}
