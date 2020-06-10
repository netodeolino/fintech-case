package com.challenge.transaction.service;

import com.challenge.transaction.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.challenge.transaction.dto.TransactionDTO;

@Service
public class TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	private final Double VALOR_MAX_TRANSACTION = 100.0;

	public ResponseEntity<String> validate(TransactionDTO transactionDTO) {
		logger.info("Validate: {}", transactionDTO.toString());

		if (transactionDTO.getPayeeId() == transactionDTO.getPayerId()) {
			return new ResponseEntity<>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		
		if (transactionDTO.getValue() > VALOR_MAX_TRANSACTION) {
			return new ResponseEntity<>(Constants.UNPROCESSABLE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(Constants.ACCEPTED, HttpStatus.ACCEPTED);
	}

}
