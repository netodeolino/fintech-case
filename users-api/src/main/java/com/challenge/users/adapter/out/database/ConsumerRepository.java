package com.challenge.users.adapter.out.database;

import java.util.Optional;

import com.challenge.users.application.port.out.ConsumerDatabasePort;
import com.challenge.users.domain.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long>, ConsumerDatabasePort {

	Optional<Consumer> findByUsername(String username);
	Optional<Consumer> findByUserId(Long user_id);

}
