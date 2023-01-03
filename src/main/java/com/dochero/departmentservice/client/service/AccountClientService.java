package com.dochero.departmentservice.client.service;

import com.dochero.departmentservice.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface AccountClientService {
    List<UserDTO> getAllAccounts();

    Map<String, UserDTO> getAllUserDTOMap();
}
