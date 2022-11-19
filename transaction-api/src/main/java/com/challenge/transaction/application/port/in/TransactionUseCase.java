package com.challenge.transaction.application.port.in;

import com.challenge.transaction.domain.dto.TransactionDTO;
import com.challenge.transaction.domain.dto.ValidationDTO;

public interface TransactionUseCase {

    ValidationDTO validate(TransactionDTO transactionDTO);
    void validateMessage(TransactionDTO transactionDTO);

}
