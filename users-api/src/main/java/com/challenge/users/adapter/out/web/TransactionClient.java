package com.challenge.users.adapter.out.web;

import com.challenge.users.application.port.out.TransactionClientPort;
import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.domain.dto.ValidationDTO;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class TransactionClient implements TransactionClientPort {

    private Logger log = LoggerFactory.getLogger(TransactionClient.class);

    @Value("${TRAN_URL:http://127.0.0.1:8001}")
    private String TRAN_URL;

    private RestTemplate restTemplate;

    public TransactionClient() {
        this.restTemplate = new RestTemplate();
    }

    @Async
    @Cacheable(value = "transactions", key = "#transactionDTO.payeeId + #transactionDTO.payerId + #transactionDTO.value")
    public CompletableFuture<ValidationDTO> validateTransaction(TransactionDTO transactionDTO) {
        log.info("Transaction: {}", transactionDTO.toString());

        HttpHeaders headersRequest = new HttpHeaders();
        headersRequest.setContentType(MediaType.APPLICATION_JSON_UTF8);

        JSONObject transJson = new JSONObject();
        transJson.put("payee_id", transactionDTO.getPayeeId());
        transJson.put("payer_id", transactionDTO.getPayerId());
        transJson.put("value", transactionDTO.getValue());

        HttpEntity<String> httpEntity = new HttpEntity<>(transJson.toJSONString(), headersRequest);

        ResponseEntity<ValidationDTO> validationDTO = restTemplate
                .exchange(TRAN_URL + "/transactions/validate", HttpMethod.POST, httpEntity, ValidationDTO.class);

        return CompletableFuture.completedFuture(validationDTO.getBody());
    }

}
