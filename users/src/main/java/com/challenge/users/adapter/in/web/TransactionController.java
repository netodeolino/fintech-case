package com.challenge.users.adapter.in.web;

import javax.validation.Valid;

import com.challenge.users.application.port.in.TransactionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.users.domain.dto.TransactionDTO;

@RestController
@RequestMapping("transactions")
public class TransactionController {

	@Autowired
	private TransactionUseCase transactionUseCase;

	@PostMapping
	public ResponseEntity<TransactionDTO> transaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionUseCase.transaction(transactionDTO));
	}
	
	@GetMapping("/{transaction_id}")
	public ResponseEntity<TransactionDTO> findById(@PathVariable Long transaction_id) {
		return ResponseEntity.ok(transactionUseCase.findById(transaction_id));
	}

}
