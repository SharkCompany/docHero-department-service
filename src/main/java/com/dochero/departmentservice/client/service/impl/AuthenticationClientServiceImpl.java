package com.dochero.departmentservice.client.service.impl;

import com.dochero.departmentservice.client.AuthenticationClient;
import com.dochero.departmentservice.client.dto.ValidateTokenResponse;
import com.dochero.departmentservice.client.service.AuthenticationClientService;
import com.dochero.departmentservice.controller.DocumentController;
import com.dochero.departmentservice.exception.ServiceClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationClientServiceImpl implements AuthenticationClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationClientServiceImpl.class);

    private final AuthenticationClient authenticationClient;

    @Autowired
    public AuthenticationClientServiceImpl(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @Override
    public ValidateTokenResponse validateToken(String token) {
        try {
            return authenticationClient.validateToken(token);
        } catch (Exception e) {
            LOGGER.error("Error when validate token", e);
            throw new ServiceClientException("User as token not found!", e);
        }
    }

    @Override
    public String testAuthService() {
        return authenticationClient.testAuthService();
    }
}
