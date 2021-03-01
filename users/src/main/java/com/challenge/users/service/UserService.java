package com.challenge.users.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.challenge.users.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.users.model.User;
import com.challenge.users.model.dto.AccountsDTO;
import com.challenge.users.model.dto.ConsumerDTO;
import com.challenge.users.model.dto.SellerDTO;
import com.challenge.users.model.dto.UserAccountsDTO;
import com.challenge.users.model.dto.UserDTO;
import com.challenge.users.repository.UserRepository;
import com.challenge.users.util.Constants;

@Service
public class UserService {

	Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private SellerService sellerService;

	public List<User> list(Optional<String> q) {
		log.info("List users by query: {}", q.orElse("blank value"));

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

	public UserAccountsDTO findById(Long userId) {
		log.info("Find by id: {}", userId);

		return this.userRepository.findById(userId).map(user -> {
			ConsumerDTO consumerDTO = this.consumerService.findByUserId(userId);
			SellerDTO sellerDTO = this.sellerService.findByUserId(userId);

			UserDTO userDTO = User.mapFromEntity(user);
			AccountsDTO accountsDTO = new AccountsDTO(consumerDTO, sellerDTO);

			return new UserAccountsDTO(userDTO, accountsDTO);
		}).orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));
	}

}
