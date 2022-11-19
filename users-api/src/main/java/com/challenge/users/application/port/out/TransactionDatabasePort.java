package com.challenge.users.application.port.out;

import com.challenge.users.domain.entity.Transaction;
import com.challenge.users.domain.enums.TransactionStatus;

import java.util.Optional;

public interface TransactionDatabasePort {

    Transaction save(Transaction user);
    Optional<Transaction> findById(Long id);
    void updateStatus(Long id, TransactionStatus status);

}
