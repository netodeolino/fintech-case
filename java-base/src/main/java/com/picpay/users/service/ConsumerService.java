package com.picpay.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay.users.exception.NotFoundException;
import com.picpay.users.model.Consumer;
import com.picpay.users.model.dto.ConsumerDTO;
import com.picpay.users.repository.ConsumerRepository;
import com.picpay.users.repository.SellerRepository;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.Constants;

@Service
public class ConsumerService {

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private UserRepository userRepository;

	public ConsumerDTO save(ConsumerDTO consumerDTO) {
		this.userRepository.findById(consumerDTO.getUser_id())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		this.sellerRepository.findByUsername(consumerDTO.getUsername())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		Consumer consumerSaved = this.consumerRepository.save(Consumer.mapFromDTO(consumerDTO));
		return Consumer.mapFromEntity(consumerSaved);
	}

	public ConsumerDTO findByUserId(long user_id) {
		return this.consumerRepository.findByUserId(user_id).map(cons -> {
			return Consumer.mapFromEntity(cons);
		}).orElse(null);
	}

}
