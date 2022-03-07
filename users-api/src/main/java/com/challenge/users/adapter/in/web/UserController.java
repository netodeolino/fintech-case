package com.challenge.users.adapter.in.web;

import java.util.List;
import java.util.Optional;

import com.challenge.users.application.port.in.ConsumerUseCase;
import com.challenge.users.application.port.in.SellerUseCase;
import com.challenge.users.application.port.in.UserUseCase;
import com.challenge.users.domain.entity.User;
import com.challenge.users.domain.dto.ConsumerDTO;
import com.challenge.users.domain.dto.SellerDTO;
import com.challenge.users.domain.dto.UserAccountsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

	private UserUseCase userUseCase;
	private ConsumerUseCase consumerUseCase;
	private SellerUseCase sellerUseCase;

	@Autowired
	public UserController(UserUseCase userUseCase, ConsumerUseCase consumerUseCase, SellerUseCase sellerUseCase) {
		this.userUseCase = userUseCase;
		this.consumerUseCase = consumerUseCase;
		this.sellerUseCase = sellerUseCase;
	}

	@GetMapping
	public List<User> list(@RequestParam Optional<String> q) {
		return userUseCase.list(q);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User save(@RequestBody User user) {
		return userUseCase.save(user);
	}

	@PostMapping("/consumers")
	@ResponseStatus(HttpStatus.CREATED)
	public ConsumerDTO saveConsumer(@RequestBody ConsumerDTO consumerDTO) {
		return consumerUseCase.save(consumerDTO);
	}

	@PostMapping("/sellers/{user_id}")
	@ResponseStatus(HttpStatus.CREATED)
	public SellerDTO saveSeller(@PathVariable Long user_id, @RequestBody SellerDTO sellerDTO) {
		return sellerUseCase.save(user_id, sellerDTO);
	}

	@GetMapping("/{user_id}")
	public UserAccountsDTO findById(@PathVariable Long user_id) {
		return userUseCase.findById(user_id);
	}
}
