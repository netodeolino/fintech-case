package com.picpay.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.picpay.users.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

	Optional<Seller> findByUsername(String username);

	Optional<Seller> findByUserId(Long user_id);

}
