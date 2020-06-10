package com.challenge.users.service;

import com.challenge.users.exception.NotFoundException;
import com.challenge.users.exception.UnprocessableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.users.model.Seller;
import com.challenge.users.model.dto.SellerDTO;
import com.challenge.users.repository.SellerRepository;
import com.challenge.users.repository.UserRepository;
import com.challenge.users.util.Constants;

@Service
public class SellerService {

	Logger logger = LoggerFactory.getLogger(SellerService.class);

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private UserRepository userRepository;

	public SellerDTO save(Long userId, SellerDTO sellerDTO) {
		logger.info("Save: {}, {}", userId, sellerDTO.toString());

		this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		this.sellerRepository.findByUsername(sellerDTO.getUsername())
				.map(s -> new UnprocessableException(Constants.UNPROCESSABLE));

		Seller sellerSaved = this.sellerRepository.save(Seller.mapFromDTO(sellerDTO));
		return Seller.mapFromEntity(sellerSaved);
	}

	public SellerDTO findByUserId(Long userId) {
		logger.info("Find by user id: {}", userId);

		return this.sellerRepository.findByUserId(userId).map(sel -> {
			return Seller.mapFromEntity(sel);
		}).orElse(null);
	}

}
