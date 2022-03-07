package com.challenge.users.adapter.out.database;

import com.challenge.users.application.port.out.TransactionDatabasePort;
import com.challenge.users.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionDatabasePort {

}
