package com.challenge.transaction.application.service;

import com.challenge.transaction.application.exception.Constants;
import com.challenge.transaction.application.exception.UnauthorizedException;
import com.challenge.transaction.application.exception.UnprocessableException;
import com.challenge.transaction.application.in.TransactionUseCase;
import com.challenge.transaction.domain.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements TransactionUseCase {

	Logger logger = LoggerFactory.getLogger(TransactionService.class);

	private final Double VALUE_MAX_BY_TRANSACTION = 100.0;

	public String validate(TransactionDTO transactionDTO) {
		logger.info("Validate: {}", transactionDTO.toString());

		if (transactionDTO.getPayeeId().equals(transactionDTO.getPayerId())) {
			throw new UnauthorizedException(Constants.UNAUTHORIZED);
		}

		if (transactionDTO.getValue() > VALUE_MAX_BY_TRANSACTION) {
			throw new UnprocessableException(Constants.UNPROCESSABLE);
		}

		return Constants.ACCEPTED;
	}

}
