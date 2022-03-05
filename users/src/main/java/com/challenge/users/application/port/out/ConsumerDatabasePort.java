package com.challenge.users.application.port.out;

import com.challenge.users.domain.entity.Consumer;

import java.util.Optional;

public interface ConsumerDatabasePort {

    Consumer save(Consumer user);
    Optional<Consumer> findByUsername(String username);
    Optional<Consumer> findByUserId(Long user_id);

}
