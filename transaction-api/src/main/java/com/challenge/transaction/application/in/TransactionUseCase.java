package com.challenge.transaction.application.in;

import com.challenge.transaction.domain.dto.TransactionDTO;
import com.challenge.transaction.domain.dto.ValidationDTO;

public interface TransactionUseCase {

    ValidationDTO validate(TransactionDTO transactionDTO);

}
