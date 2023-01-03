package com.dochero.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
    private String id;
    private String fullName;
    private String email;
    private List<String> departmentIDs;
    private String roleName;
}
