package com.challenge.users.application.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.challenge.users.application.exception.NotFoundException;
import com.challenge.users.application.port.in.UserUseCase;
import com.challenge.users.application.port.out.UserDatabasePort;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.users.domain.entity.User;
import com.challenge.users.domain.dto.AccountsDTO;
import com.challenge.users.domain.dto.ConsumerDTO;
import com.challenge.users.domain.dto.SellerDTO;
import com.challenge.users.domain.dto.UserAccountsDTO;
import com.challenge.users.domain.dto.UserDTO;
import com.challenge.users.application.exception.Constants;

@Service
public class UserService implements UserUseCase {

	private Logger log = LoggerFactory.getLogger(UserService.class);

	private UserDatabasePort userDatabasePort;
	private ConsumerService consumerService;
	private SellerService sellerService;
	private ModelMapper modelMapper;

	@Autowired
	public UserService(
			UserDatabasePort userDatabasePort, ConsumerService consumerService,
			SellerService sellerService, ModelMapper modelMapper
	) {
		this.userDatabasePort = userDatabasePort;
		this.consumerService = consumerService;
		this.sellerService = sellerService;
		this.modelMapper = modelMapper;
	}

	public List<User> list(Optional<String> q) {
		log.info("List users by query: {}", q.orElse("blank value"));

		if (q.isPresent()) {
			Optional<List<User>> optFullName = userDatabasePort.findByFullNameStartingWithIgnoreCase(q.get());
			if (optFullName.isPresent()) {
				return optFullName.get();
			}

			Optional<List<User>> optUserName = userDatabasePort.findByUserName(q.get());
			if (optUserName.isPresent()) {
				return optUserName.get();
			}

			return Collections.emptyList();
		}

		return userDatabasePort.findAll();
	}

	public User save(User user) {
		return userDatabasePort.save(user);
	}

	public UserAccountsDTO findById(Long userId) {
		log.info("Find by id: {}", userId);

		return userDatabasePort.findById(userId).map(user -> {
			ConsumerDTO consumerDTO = consumerService.findByUserId(userId);
			SellerDTO sellerDTO = sellerService.findByUserId(userId);

			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			AccountsDTO accountsDTO = new AccountsDTO(consumerDTO, sellerDTO);

			return new UserAccountsDTO(userDTO, accountsDTO);
		}).orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));
	}

}
