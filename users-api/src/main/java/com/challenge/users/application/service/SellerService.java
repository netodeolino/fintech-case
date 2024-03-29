package com.challenge.users.application.service;

import com.challenge.users.application.exception.NotFoundException;
import com.challenge.users.application.exception.UnprocessableException;
import com.challenge.users.application.port.in.SellerUseCase;
import com.challenge.users.application.port.out.SellerDatabasePort;
import com.challenge.users.application.port.out.UserDatabasePort;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.users.domain.entity.Seller;
import com.challenge.users.domain.dto.SellerDTO;
import com.challenge.users.application.exception.Constants;

@Service
public class SellerService implements SellerUseCase {

	private final Logger log = LoggerFactory.getLogger(SellerService.class);

	private final SellerDatabasePort sellerDatabasePort;
	private final UserDatabasePort userDatabasePort;
	private final ModelMapper modelMapper;

	@Autowired
	public SellerService(SellerDatabasePort sellerDatabasePort, UserDatabasePort userDatabasePort, ModelMapper modelMapper) {
		this.sellerDatabasePort = sellerDatabasePort;
		this.userDatabasePort = userDatabasePort;
		this.modelMapper = modelMapper;
	}

	public SellerDTO save(Long userId, SellerDTO sellerDTO) {
		log.info("Save: {}, {}", userId, sellerDTO.toString());

		userDatabasePort
				.findById(userId)
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		sellerDatabasePort
				.findByUsername(sellerDTO.getUsername())
				.map(s -> new UnprocessableException(Constants.UNPROCESSABLE));

		Seller seller = modelMapper.map(sellerDTO, Seller.class);
		seller = sellerDatabasePort.save(seller);
		return modelMapper.map(seller, SellerDTO.class);
	}

	public SellerDTO findByUserId(Long userId) {
		log.info("Find by user id: {}", userId);

		return sellerDatabasePort
				.findByUserId(userId)
				.map(sel -> modelMapper.map(sel, SellerDTO.class))
				.orElse(null);
	}

}
