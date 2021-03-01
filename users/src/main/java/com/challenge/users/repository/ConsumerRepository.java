package com.challenge.users.repository;

import java.util.Optional;

import com.challenge.users.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

	Optional<Consumer> findByUsername(String username);

	Optional<Consumer> findByUserId(Long user_id);

}
