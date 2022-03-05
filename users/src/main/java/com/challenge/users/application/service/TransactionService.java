package com.challenge.users.application.service;

import com.challenge.users.adapter.out.web.TransactionClient;
import com.challenge.users.application.exception.NotFoundException;
import com.challenge.users.application.port.in.TransactionUseCase;
import com.challenge.users.application.port.out.TransactionDatabasePort;
import com.challenge.users.domain.entity.Transaction;
import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.application.exception.Constants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionService implements TransactionUseCase {

	private Logger log = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private TransactionDatabasePort transactionDatabasePort;

	@Autowired
	private TransactionClient transactionClient;

	@Autowired
	private ModelMapper modelMapper;

	public TransactionDTO transaction(TransactionDTO transactionDTO) {
		log.info("Transaction: {}", transactionDTO.toString());

		transactionClient.validateTransaction(transactionDTO);

		transactionDTO.setTransactionDate(new Date());

		Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
		transaction = transactionDatabasePort.save(transaction);
		return modelMapper.map(transaction, TransactionDTO.class);
	}

	public TransactionDTO findById(Long transactionId) {
		log.info("Find by id: {}", transactionId);

		Transaction transaction = transactionDatabasePort
				.findById(transactionId)
				.orElseThrow(() -> new NotFoundException(Constants.TRANSACTION_NOT_FOUND));

		return new TransactionDTO(transaction.getId(), transaction.getPayee().getId(), transaction.getPayer().getId(),
				transaction.getValue(), transaction.getTransactionDate());
	}

}
