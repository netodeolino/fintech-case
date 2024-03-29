package com.challenge.users.application.service;

import com.challenge.users.application.exception.NotFoundException;
import com.challenge.users.application.exception.UnprocessableException;
import com.challenge.users.application.port.in.ConsumerUseCase;
import com.challenge.users.application.port.out.ConsumerDatabasePort;
import com.challenge.users.application.port.out.UserDatabasePort;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.users.domain.entity.Consumer;
import com.challenge.users.domain.dto.ConsumerDTO;
import com.challenge.users.application.exception.Constants;

@Service
public class ConsumerService implements ConsumerUseCase {

	private final Logger log = LoggerFactory.getLogger(ConsumerService.class);

	private final ConsumerDatabasePort consumerDatabasePort;
	private final UserDatabasePort userDatabasePort;
	private final ModelMapper modelMapper;

	@Autowired
	public ConsumerService(ConsumerDatabasePort consumerDatabasePort, UserDatabasePort userDatabasePort, ModelMapper modelMapper) {
		this.consumerDatabasePort = consumerDatabasePort;
		this.userDatabasePort = userDatabasePort;
		this.modelMapper = modelMapper;
	}

	public ConsumerDTO save(ConsumerDTO consumerDTO) {
		log.info("Save: {}", consumerDTO.toString());

		userDatabasePort
				.findById(consumerDTO.getUserId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		consumerDatabasePort
				.findByUsername(consumerDTO.getUsername())
				.map(c -> new UnprocessableException(Constants.UNPROCESSABLE));

		Consumer consumer = modelMapper.map(consumerDTO, Consumer.class);
		consumer = consumerDatabasePort.save(consumer);
		return modelMapper.map(consumer, ConsumerDTO.class);
	}

	public ConsumerDTO findByUserId(long userId) {
		log.info("Find by user id: {}", userId);

		return consumerDatabasePort
				.findByUserId(userId)
				.map(cons -> modelMapper.map(cons, ConsumerDTO.class))
				.orElse(null);
	}

}
