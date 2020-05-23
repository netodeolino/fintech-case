package com.picpay.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.picpay.users.model.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

	Optional<Consumer> findByUsername(String username);

	Optional<Consumer> findByUserId(Long user_id);

}
