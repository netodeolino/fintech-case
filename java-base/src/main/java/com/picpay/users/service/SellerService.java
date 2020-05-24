package com.picpay.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay.users.exception.NotFoundException;
import com.picpay.users.exception.UnprocessableException;
import com.picpay.users.model.Seller;
import com.picpay.users.model.dto.SellerDTO;
import com.picpay.users.repository.SellerRepository;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.Constants;

@Service
public class SellerService {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private UserRepository userRepository;

	public SellerDTO save(Long user_id, SellerDTO sellerDTO) {
		this.userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));

		this.sellerRepository.findByUsername(sellerDTO.getUsername())
				.map(s -> new UnprocessableException(Constants.UNPROCESSABLE));

		Seller sellerSaved = this.sellerRepository.save(Seller.mapFromDTO(sellerDTO));
		return Seller.mapFromEntity(sellerSaved);
	}

	public SellerDTO findByUserId(Long user_id) {
		return this.sellerRepository.findByUserId(user_id).map(sel -> {
			return Seller.mapFromEntity(sel);
		}).orElse(null);
	}

}
