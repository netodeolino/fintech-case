package com.challenge.users.application.port.out;

import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.domain.dto.ValidationDTO;

import java.util.concurrent.CompletableFuture;

public interface TransactionClientPort {

    CompletableFuture<ValidationDTO> validateTransaction(TransactionDTO transactionDTO);

}
