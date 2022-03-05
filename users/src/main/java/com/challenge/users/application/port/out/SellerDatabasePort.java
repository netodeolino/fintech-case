package com.challenge.users.application.port.out;

import com.challenge.users.domain.entity.Seller;

import java.util.Optional;

public interface SellerDatabasePort {

    Seller save(Seller user);
    Optional<Seller> findByUsername(String username);
    Optional<Seller> findByUserId(Long user_id);

}
