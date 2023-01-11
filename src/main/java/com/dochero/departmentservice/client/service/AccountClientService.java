package com.dochero.departmentservice.client.service;

import com.dochero.departmentservice.client.dto.AccountFileHistoryDTO;
import com.dochero.departmentservice.client.dto.AccountFileHistoryUpdateRequest;
import com.dochero.departmentservice.dto.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface AccountClientService {
    List<UserDTO> getAllAccounts();

    Map<String, UserDTO> getAllUserDTOMap();

    List<AccountFileHistoryDTO> getAccountFileHistory(String userId);

    AccountFileHistoryDTO updateFileHistory(String userId, AccountFileHistoryUpdateRequest request);
}
