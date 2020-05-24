package com.picpay.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay.users.exception.NotFoundException;
import com.picpay.users.exception.UnprocessableException;
import com.picpay.users.model.Consumer;
import com.picpay.users.model.dto.ConsumerDTO;
import com.picpay.users.repository.ConsumerRepository;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.Constants;

@Service
public class ConsumerService {

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private UserRepository userRepository;

	public ConsumerDTO save(ConsumerDTO consumerDTO) {
		this.userRepository.findById(consumerDTO.getUserId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		this.consumerRepository.findByUsername(consumerDTO.getUsername())
				.map(c -> new UnprocessableException(Constants.UNPROCESSABLE));

		Consumer consumerSaved = this.consumerRepository.save(Consumer.mapFromDTO(consumerDTO));
		return Consumer.mapFromEntity(consumerSaved);
	}

	public ConsumerDTO findByUserId(long user_id) {
		return this.consumerRepository.findByUserId(user_id).map(cons -> {
			return Consumer.mapFromEntity(cons);
		}).orElse(null);
	}

}
