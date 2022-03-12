package com.challenge.users.adapter.out.web.feign;

import com.challenge.users.config.FeignConfig;
import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.domain.dto.ValidationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "transactions", url = "${TRAN_URL:http://127.0.0.1:8001}", configuration = FeignConfig.class)
public interface TransactionFeignClient {

    @PostMapping(value = "/transactions/validate", consumes = "application/json", produces = "application/json")
    ValidationDTO validateTransaction(@RequestBody TransactionDTO transactionDTO);

}
