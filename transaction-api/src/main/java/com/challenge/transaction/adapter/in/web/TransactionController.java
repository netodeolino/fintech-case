package com.challenge.transaction.adapter.in.web;

import com.challenge.transaction.application.port.in.TransactionUseCase;
import com.challenge.transaction.domain.dto.ValidationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.transaction.domain.dto.TransactionDTO;

@RestController
@RequestMapping("transactions")
public class TransactionController {

	private final TransactionUseCase transactionUseCase;

	@Autowired
	public TransactionController(TransactionUseCase transactionUseCase) {
		this.transactionUseCase = transactionUseCase;
	}

	@PostMapping("/validate")
	public ValidationDTO validate(@RequestBody TransactionDTO transactionDTO) {
		return transactionUseCase.validate(transactionDTO);
	}

}
