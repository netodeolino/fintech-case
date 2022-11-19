package com.challenge.transaction.application.port.out;

import com.challenge.transaction.domain.dto.TransactionDTO;

public interface TransactionStreamPort {

    void sendTransactionValidatedMessage(TransactionDTO transactionDTO);

}
