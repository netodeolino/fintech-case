package com.challenge.users.controller;

import java.util.List;
import java.util.Optional;

import com.challenge.users.model.User;
import com.challenge.users.model.dto.ConsumerDTO;
import com.challenge.users.model.dto.SellerDTO;
import com.challenge.users.model.dto.UserAccountsDTO;
import com.challenge.users.service.ConsumerService;
import com.challenge.users.service.SellerService;
import com.challenge.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private SellerService sellerService;

	@GetMapping
	public ResponseEntity<List<User>> list(@RequestParam Optional<String> q) {
		return ResponseEntity.ok(this.userService.list(q));
	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(user));
	}

	@PostMapping("/consumers")
	public ResponseEntity<ConsumerDTO> saveConsumer(@RequestBody ConsumerDTO consumerDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.consumerService.save(consumerDTO));
	}

	@PostMapping("/sellers/{user_id}")
	public ResponseEntity<SellerDTO> saveSeller(@PathVariable Long user_id, @RequestBody SellerDTO sellerDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.sellerService.save(user_id, sellerDTO));
	}

	@GetMapping("/{user_id}")
	public ResponseEntity<UserAccountsDTO> findById(@PathVariable Long user_id) {
		return ResponseEntity.ok(this.userService.findById(user_id));
	}
}
