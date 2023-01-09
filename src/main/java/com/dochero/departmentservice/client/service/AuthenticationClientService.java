package com.dochero.departmentservice.client.service;

import com.dochero.departmentservice.client.dto.ValidateTokenResponse;

import javax.ws.rs.PathParam;

public interface AuthenticationClientService {

    ValidateTokenResponse validateToken(String credentials);

    String testAuthService();
}
