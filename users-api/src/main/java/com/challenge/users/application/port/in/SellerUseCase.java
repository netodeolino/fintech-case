package com.challenge.users.application.port.in;

import com.challenge.users.domain.dto.SellerDTO;

public interface SellerUseCase {

    SellerDTO save(Long userId, SellerDTO sellerDTO);
    SellerDTO findByUserId(Long userId);

}
