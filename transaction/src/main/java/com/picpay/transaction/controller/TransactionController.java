package com.picpay.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpay.transaction.dto.TransactionDTO;
import com.picpay.transaction.service.TransactionService;


@RestController
@RequestMapping("transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/validate")
	public ResponseEntity<String> validate(@RequestBody TransactionDTO transactionDTO) {
		return this.transactionService.validate(transactionDTO);
	}

}
