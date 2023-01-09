package com.dochero.departmentservice.client;

import com.dochero.departmentservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "account-service", url = "http://103.48.192.223:40002")
//@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/accounts")
    List<UserDTO> getAllAccounts();
}
