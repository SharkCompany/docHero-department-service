package com.dochero.departmentservice.client.service.impl;

import com.dochero.departmentservice.client.AccountClient;
import com.dochero.departmentservice.client.service.AccountClientService;
import com.dochero.departmentservice.dto.UserDTO;
import com.dochero.departmentservice.exception.ServiceClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountClientServiceImpl implements AccountClientService {
    private final AccountClient accountClient;

    @Autowired
    public AccountClientServiceImpl(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @Override
    public List<UserDTO> getAllAccounts() {
        return accountClient.getAllAccounts();
    }

    @Override
    public Map<String, UserDTO> getAllUserDTOMap() {
        try {
            List<UserDTO> allAccounts = accountClient.getAllAccounts();
            return allAccounts.stream().collect(Collectors.toMap(UserDTO::getId, Function.identity()));
        } catch (Exception e) {
            throw new ServiceClientException("Error when get all userDTO map", e);
        }
    }
}
