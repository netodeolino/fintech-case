package com.challenge.users.adapter.out.database;

import java.util.Optional;

import com.challenge.users.application.port.out.SellerDatabasePort;
import com.challenge.users.domain.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>, SellerDatabasePort {

	Optional<Seller> findByUsername(String username);
	Optional<Seller> findByUserId(Long user_id);

}
