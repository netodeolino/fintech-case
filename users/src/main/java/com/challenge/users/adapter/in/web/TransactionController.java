package com.challenge.users.adapter.in.web;

import javax.validation.Valid;

import com.challenge.users.application.port.in.TransactionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.challenge.users.domain.dto.TransactionDTO;

@RestController
@RequestMapping("transactions")
public class TransactionController {

	private TransactionUseCase transactionUseCase;

	@Autowired
	public TransactionController(TransactionUseCase transactionUseCase) {
		this.transactionUseCase = transactionUseCase;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionDTO transaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		return transactionUseCase.transaction(transactionDTO);
	}

	@GetMapping("/{transaction_id}")
	public TransactionDTO findById(@PathVariable Long transaction_id) {
		return transactionUseCase.findById(transaction_id);
	}

}
