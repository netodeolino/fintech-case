package com.challenge.users.cliente;

import com.challenge.users.exception.UnprocessableException;
import com.challenge.users.model.dto.TransactionDTO;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionClient {

    Logger log = LoggerFactory.getLogger(TransactionClient.class);

    @Value("${TRAN_URL:http://127.0.0.1:8001}")
    private String TRAN_URL;

    private RestTemplate restTemplate;

    public TransactionClient() {
        this.restTemplate = new RestTemplate();
    }

    @Cacheable(value = "transactions", key = "#transactionDTO")
    public void validateTransaction(TransactionDTO transactionDTO) {
        log.info("Transaction: {}", transactionDTO.toString());

        try {
            HttpHeaders headersRequest = new HttpHeaders();
            headersRequest.setContentType(MediaType.APPLICATION_JSON_UTF8);

            JSONObject transJson = new JSONObject();
            transJson.put("payee_id", transactionDTO.getPayeeId());
            transJson.put("payer_id", transactionDTO.getPayerId());
            transJson.put("value", transactionDTO.getValue());

            HttpEntity<String> httpEntity = new HttpEntity<>(transJson.toJSONString(), headersRequest);

            this.restTemplate.exchange(
                    this.TRAN_URL + "/transactions/validate", HttpMethod.POST, httpEntity, String.class);
        } catch (Exception e) {
            throw new UnprocessableException(e.getMessage());
        }
    }

}
