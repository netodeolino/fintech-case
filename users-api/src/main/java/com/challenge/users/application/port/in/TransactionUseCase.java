package com.challenge.users.application.port.in;

import com.challenge.users.domain.dto.TransactionDTO;

public interface TransactionUseCase {

    TransactionDTO transaction(TransactionDTO transactionDTO);
    TransactionDTO findById(Long transactionId);
    void update(TransactionDTO transactionDTO);

}
