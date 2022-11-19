package com.challenge.users.adapter.out.database;

import com.challenge.users.application.port.out.TransactionDatabasePort;
import com.challenge.users.domain.entity.Transaction;
import com.challenge.users.domain.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionDatabasePort {

    @Modifying
    @Query("UPDATE Transaction t SET t.status = ?2 WHERE t.id = ?1")
    void updateStatus(Long id, TransactionStatus status);

}
