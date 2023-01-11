package com.dochero.departmentservice.client;

import com.dochero.departmentservice.client.dto.AccountFileHistoryDTO;
import com.dochero.departmentservice.client.dto.AccountFileHistoryUpdateRequest;
import com.dochero.departmentservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name = "account-service", url = "http://103.48.192.223:40002")
@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/accounts")
    List<UserDTO> getAllAccounts();

    @GetMapping("/account/{userId}/file-history")
    List<AccountFileHistoryDTO> getAccountFileHistory(@PathVariable("userId") String userId);

    @PutMapping("/account/{id}/file-history")
    AccountFileHistoryDTO updateFileHistory(@PathVariable("id") String userId, @RequestBody AccountFileHistoryUpdateRequest request);
}
