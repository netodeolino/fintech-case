package com.challenge.users.adapter.out.web;

import com.challenge.users.adapter.out.web.feign.TransactionFeignClient;
import com.challenge.users.application.exception.Constants;
import com.challenge.users.application.exception.UnprocessableException;
import com.challenge.users.application.port.out.TransactionClientPort;
import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.domain.dto.ValidationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransactionClient implements TransactionClientPort {

    private Logger log = LoggerFactory.getLogger(TransactionClient.class);

    private TransactionFeignClient transactionFeignClient;

    @Autowired
    public TransactionClient(TransactionFeignClient transactionFeignClient) {
        this.transactionFeignClient = transactionFeignClient;
    }

    @Cacheable(value = "transactions", key = "#transactionDTO.payeeId + #transactionDTO.payerId + #transactionDTO.value")
    public ValidationDTO validateTransaction(TransactionDTO transactionDTO) {
        log.info("Transaction: {}", transactionDTO.toString());

        CompletableFuture<ValidationDTO> validFuture = futureValidateTransaction(transactionDTO);

        try {
            return validFuture.get();
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new UnprocessableException(Constants.UNPROCESSABLE);
        }
    }

    @Async
    private CompletableFuture<ValidationDTO> futureValidateTransaction(TransactionDTO transactionDTO) {
        ValidationDTO validationDTO = transactionFeignClient.validateTransaction(transactionDTO);
        log.info("Validated: {}", validationDTO.toString());
        return CompletableFuture.completedFuture(validationDTO);
    }

}
