package com.challenge.users.adapter.in.web;

import javax.validation.Valid;

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
import com.challenge.users.application.service.TransactionService;

@RestController
@RequestMapping("transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<TransactionDTO> transaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.transactionService.transaction(transactionDTO));
	}
	
	@GetMapping("/{transaction_id}")
	public ResponseEntity<TransactionDTO> findById(@PathVariable Long transaction_id) {
		return ResponseEntity.ok(this.transactionService.findById(transaction_id));
	}
}
