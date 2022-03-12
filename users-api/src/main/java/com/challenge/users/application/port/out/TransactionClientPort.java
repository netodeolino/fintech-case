package com.challenge.users.application.port.out;

import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.domain.dto.ValidationDTO;

public interface TransactionClientPort {

    ValidationDTO validateTransaction(TransactionDTO transactionDTO);

}
