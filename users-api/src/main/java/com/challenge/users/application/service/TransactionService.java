package com.challenge.users.application.service;

import com.challenge.users.application.exception.Constants;
import com.challenge.users.application.exception.NotFoundException;
import com.challenge.users.application.exception.UnprocessableException;
import com.challenge.users.application.port.in.TransactionUseCase;
import com.challenge.users.application.port.out.TransactionDatabasePort;
import com.challenge.users.application.port.out.TransactionStreamPort;
import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.domain.entity.Transaction;
import com.challenge.users.domain.enums.TransactionStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TransactionService implements TransactionUseCase {

	private final Logger log = LoggerFactory.getLogger(TransactionService.class);

	private final TransactionDatabasePort transactionDatabasePort;
	private final TransactionStreamPort transactionStreamPort;
	private final ModelMapper modelMapper;

	@Autowired
	public TransactionService(
			TransactionDatabasePort transactionDatabasePort,
			TransactionStreamPort transactionStreamPort,
			ModelMapper modelMapper
	) {
		this.transactionDatabasePort = transactionDatabasePort;
		this.transactionStreamPort = transactionStreamPort;
		this.modelMapper = modelMapper;
	}

	@Transactional
	public TransactionDTO transaction(TransactionDTO transactionDTO) {
		log.info("Transaction: {}", transactionDTO.toString());

		transactionDTO.setStatus(TransactionStatus.CREATED.name());
		transactionDTO.setTransactionDate(new Date());

		Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);

		transaction = transactionDatabasePort.save(transaction);

		transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

		transactionStreamPort.sendTransactionValidateMessage(transactionDTO);

		return transactionDTO;
	}

	public TransactionDTO findById(Long transactionId) {
		log.info("Find by id: {}", transactionId);

		Transaction transaction = transactionDatabasePort
				.findById(transactionId)
				.orElseThrow(() -> new NotFoundException(Constants.TRANSACTION_NOT_FOUND));

		return new TransactionDTO(transaction.getId(), transaction.getPayee().getId(), transaction.getPayer().getId(),
				transaction.getValue(), transaction.getTransactionDate(), transaction.getStatus().name());
	}

	@Transactional
	public void update(TransactionDTO transactionDTO) {
		Long id = transactionDTO.getId();
		String status = transactionDTO.getStatus();

		log.info("Updating transaction id: {} to status : {}", id, status);

		if (!isValidStatus(status)) throw new UnprocessableException("Status invalid!");

		transactionDatabasePort.updateStatus(id, TransactionStatus.valueOf(status));
	}

	private boolean isValidStatus(String status) {
		return status.equals(TransactionStatus.APPROVED.name()) || status.equals(TransactionStatus.REFUSED.name());
	}

}
