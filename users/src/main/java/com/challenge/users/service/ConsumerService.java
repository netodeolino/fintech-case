package com.challenge.users.service;

import com.challenge.users.exception.NotFoundException;
import com.challenge.users.exception.UnprocessableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.users.model.Consumer;
import com.challenge.users.model.dto.ConsumerDTO;
import com.challenge.users.repository.ConsumerRepository;
import com.challenge.users.repository.UserRepository;
import com.challenge.users.util.Constants;

@Service
public class ConsumerService {

	Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private UserRepository userRepository;

	public ConsumerDTO save(ConsumerDTO consumerDTO) {
		logger.info("Save: {}", consumerDTO.toString());

		this.userRepository.findById(consumerDTO.getUserId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		this.consumerRepository.findByUsername(consumerDTO.getUsername())
				.map(c -> new UnprocessableException(Constants.UNPROCESSABLE));

		Consumer consumerSaved = this.consumerRepository.save(Consumer.mapFromDTO(consumerDTO));
		return Consumer.mapFromEntity(consumerSaved);
	}

	public ConsumerDTO findByUserId(long userId) {
		logger.info("Find by user id: {}", userId);

		return this.consumerRepository.findByUserId(userId).map(cons -> {
			return Consumer.mapFromEntity(cons);
		}).orElse(null);
	}

}
