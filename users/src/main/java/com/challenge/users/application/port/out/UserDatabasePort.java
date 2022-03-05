package com.challenge.users.application.port.out;

import com.challenge.users.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDatabasePort {

    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<List<User>> findByUserName(String param);
    Optional<List<User>> findByFullNameStartingWithIgnoreCase(String param);

}
