package com.challenge.users.application.service;

import com.challenge.users.application.exception.NotFoundException;
import com.challenge.users.application.exception.UnprocessableException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.users.domain.entity.Consumer;
import com.challenge.users.domain.dto.ConsumerDTO;
import com.challenge.users.adapter.out.database.ConsumerRepository;
import com.challenge.users.adapter.out.database.UserRepository;
import com.challenge.users.application.exception.Constants;

@Service
public class ConsumerService {

	private Logger log = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ConsumerDTO save(ConsumerDTO consumerDTO) {
		log.info("Save: {}", consumerDTO.toString());

		userRepository
				.findById(consumerDTO.getUserId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		consumerRepository
				.findByUsername(consumerDTO.getUsername())
				.map(c -> new UnprocessableException(Constants.UNPROCESSABLE));

		Consumer consumer = modelMapper.map(consumerDTO, Consumer.class);
		consumer = consumerRepository.save(consumer);
		return modelMapper.map(consumer, ConsumerDTO.class);
	}

	public ConsumerDTO findByUserId(long userId) {
		log.info("Find by user id: {}", userId);

		return consumerRepository
				.findByUserId(userId)
				.map(cons -> modelMapper.map(cons, ConsumerDTO.class))
				.orElse(null);
	}

}
