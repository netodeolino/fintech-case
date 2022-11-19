package com.challenge.transaction.application.service;

import com.challenge.transaction.application.exception.Constants;
import com.challenge.transaction.application.exception.UnauthorizedException;
import com.challenge.transaction.application.exception.UnprocessableException;
import com.challenge.transaction.application.port.in.TransactionUseCase;
import com.challenge.transaction.application.port.out.TransactionStreamPort;
import com.challenge.transaction.domain.dto.TransactionDTO;
import com.challenge.transaction.domain.dto.ValidationDTO;
import com.challenge.transaction.domain.enums.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements TransactionUseCase {

	private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	private final TransactionStreamPort transactionStreamPort;

	private static final double VALUE_MAX_BY_TRANSACTION = 100.0;

	@Autowired
	public TransactionService(TransactionStreamPort transactionStreamPort) {
		this.transactionStreamPort = transactionStreamPort;
	}

	public ValidationDTO validate(TransactionDTO transactionDTO) {
		logger.info("Validate: {}", transactionDTO.toString());

		if (transactionDTO.getPayeeId().equals(transactionDTO.getPayerId())) {
			throw new UnauthorizedException(Constants.UNAUTHORIZED);
		}

		if (transactionDTO.getValue() > VALUE_MAX_BY_TRANSACTION) {
			throw new UnprocessableException(Constants.UNPROCESSABLE);
		}

		return new ValidationDTO(true);
	}

	public void validateMessage(TransactionDTO transactionDTO) {
		logger.info("Validate: {}", transactionDTO.toString());

		if (transactionDTO.getPayeeId().equals(transactionDTO.getPayerId())
				|| transactionDTO.getValue() > VALUE_MAX_BY_TRANSACTION) {
			transactionDTO.setStatus(TransactionStatus.REFUSED.name());
		} else {
			transactionDTO.setStatus(TransactionStatus.APPROVED.name());
		}

		transactionStreamPort.sendTransactionValidatedMessage(transactionDTO);
	}

}
