package com.dochero.departmentservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ValidateTokenResponse {
    private String userId;
    private String email;
    private String roleName;
    private String fullName;
    private List<String> departmentIDs;
}
