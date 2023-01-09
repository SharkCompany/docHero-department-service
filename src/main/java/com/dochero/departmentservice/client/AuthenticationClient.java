package com.dochero.departmentservice.client;

import com.dochero.departmentservice.client.dto.ValidateTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "authentication-service", path = "/auth", url = "http://103.48.192.223:40003")
//@FeignClient(name = "authentication-service", path = "auth")
public interface AuthenticationClient {

    @GetMapping("/validate-token")
    ValidateTokenResponse validateToken(@RequestParam("token") String token);

    @GetMapping()
    String testAuthService();


}
