package com.challenge.users.application.service;

import com.challenge.users.application.exception.NotFoundException;
import com.challenge.users.application.exception.UnprocessableException;
import com.challenge.users.application.port.in.TransactionUseCase;
import com.challenge.users.application.port.out.TransactionClientPort;
import com.challenge.users.application.port.out.TransactionDatabasePort;
import com.challenge.users.domain.dto.ValidationDTO;
import com.challenge.users.domain.entity.Transaction;
import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.application.exception.Constants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class TransactionService implements TransactionUseCase {

	private Logger log = LoggerFactory.getLogger(TransactionService.class);

	private TransactionDatabasePort transactionDatabasePort;
	private TransactionClientPort transactionClientPort;
	private ModelMapper modelMapper;

	@Autowired
	public TransactionService(
			TransactionDatabasePort transactionDatabasePort,
			TransactionClientPort transactionClientPort,
			ModelMapper modelMapper
	) {
		this.transactionDatabasePort = transactionDatabasePort;
		this.transactionClientPort = transactionClientPort;
		this.modelMapper = modelMapper;
	}

	public TransactionDTO transaction(TransactionDTO transactionDTO) {
		log.info("Transaction: {}", transactionDTO.toString());

		try {
			validateTransaction(transactionDTO);

			transactionDTO.setTransactionDate(new Date());

			Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
			transaction = transactionDatabasePort.save(transaction);

			return modelMapper.map(transaction, TransactionDTO.class);
		} catch (InterruptedException | ExecutionException | UnprocessableException e) {
			throw new UnprocessableException(Constants.UNPROCESSABLE);
		}
	}

	public TransactionDTO findById(Long transactionId) {
		log.info("Find by id: {}", transactionId);

		Transaction transaction = transactionDatabasePort
				.findById(transactionId)
				.orElseThrow(() -> new NotFoundException(Constants.TRANSACTION_NOT_FOUND));

		return new TransactionDTO(transaction.getId(), transaction.getPayee().getId(), transaction.getPayer().getId(),
				transaction.getValue(), transaction.getTransactionDate());
	}

	private void validateTransaction(TransactionDTO transactionDTO) throws ExecutionException, InterruptedException {
		CompletableFuture<ValidationDTO> completableFuture = transactionClientPort.validateTransaction(transactionDTO);
		if (!completableFuture.get().isValid()) throw new UnprocessableException(Constants.UNPROCESSABLE);
	}

}
