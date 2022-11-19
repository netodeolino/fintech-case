package com.challenge.users.application.port.out;

import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.domain.dto.ValidationDTO;

public interface TransactionStreamPort {

    void sendTransactionValidateMessage(TransactionDTO transactionDTO);

}
