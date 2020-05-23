package com.picpay.users.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay.users.exception.NotFoundException;
import com.picpay.users.model.User;
import com.picpay.users.model.dto.AccountsDTO;
import com.picpay.users.model.dto.ConsumerDTO;
import com.picpay.users.model.dto.SellerDTO;
import com.picpay.users.model.dto.UserAccountsDTO;
import com.picpay.users.model.dto.UserDTO;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.Constants;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private SellerService sellerService;

	public List<User> list(Optional<String> q) {
		if (q.isPresent()) {
			Optional<List<User>> optFullName = this.userRepository.findByFullNameStartingWithIgnoreCase(q.get());
			if (optFullName.isPresent()) {
				return optFullName.get();
			}

			Optional<List<User>> optUserName = this.userRepository.findByUserName(q.get());
			if (optUserName.isPresent()) {
				return optUserName.get();
			}

			return Collections.emptyList();
		}

		return this.userRepository.findAll();
	}

	public User save(User user) {
		return this.userRepository.save(user);
	}

	public UserAccountsDTO findById(Long user_id) {
		return this.userRepository.findById(user_id).map(user -> {
			ConsumerDTO consumerDTO = this.consumerService.findByUserId(user_id);
			SellerDTO sellerDTO = this.sellerService.findByUserId(user_id);

			UserDTO userDTO = User.mapFromEntity(user);
			AccountsDTO accountsDTO = new AccountsDTO(consumerDTO, sellerDTO);

			return new UserAccountsDTO(userDTO, accountsDTO);
		}).orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));
	}

}
