package com.challenge.users.service;

import com.challenge.users.cliente.TransactionClient;
import com.challenge.users.exception.NotFoundException;
import com.challenge.users.model.Transaction;
import com.challenge.users.model.dto.TransactionDTO;
import com.challenge.users.repository.TransactionRepository;
import com.challenge.users.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionService {

	Logger log = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionClient transactionClient;

	public TransactionDTO transaction(TransactionDTO transactionDTO) {
		log.info("Transaction: {}", transactionDTO.toString());

		transactionClient.validateTransaction(transactionDTO);

		transactionDTO.setTransactionDate(new Date());
		Transaction transactionSaved = transactionRepository.save(Transaction.mapFromDTO(transactionDTO));

		return Transaction.mapFromEntity(transactionSaved);
	}

	public TransactionDTO findById(Long transactionId) {
		log.info("Find by id: {}", transactionId);

		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new NotFoundException(Constants.TRANSACTION_NOT_FOUND));

		return new TransactionDTO(transaction.getId(), transaction.getPayee().getId(), transaction.getPayer().getId(),
				transaction.getValue(), transaction.getTransactionDate());
	}

}
