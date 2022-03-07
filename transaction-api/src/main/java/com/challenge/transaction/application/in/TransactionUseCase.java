package com.challenge.transaction.application.in;

import com.challenge.transaction.domain.dto.TransactionDTO;

public interface TransactionUseCase {

    String validate(TransactionDTO transactionDTO);

}
