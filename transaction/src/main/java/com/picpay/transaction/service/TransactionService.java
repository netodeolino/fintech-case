package com.picpay.transaction.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.picpay.transaction.dto.TransactionDTO;
import com.picpay.transaction.util.Constants;

@Service
public class TransactionService {
	
	private final Double VALOR_MAX_TRANSACTION = 100.0;

	public ResponseEntity<String> validate(TransactionDTO transactionDTO) {
		if (transactionDTO.getPayeeId() == transactionDTO.getPayerId()) {
			return new ResponseEntity<>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		
		if (transactionDTO.getValue() > VALOR_MAX_TRANSACTION) {
			return new ResponseEntity<>(Constants.UNPROCESSABLE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(Constants.ACCEPTED, HttpStatus.ACCEPTED);
	}

}
