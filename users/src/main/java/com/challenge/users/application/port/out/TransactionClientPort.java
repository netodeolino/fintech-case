package com.challenge.users.application.port.out;

import com.challenge.users.domain.dto.TransactionDTO;

public interface TransactionClientPort {

    void validateTransaction(TransactionDTO transactionDTO);

}
