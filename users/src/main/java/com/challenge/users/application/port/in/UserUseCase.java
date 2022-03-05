package com.challenge.users.application.port.in;

import com.challenge.users.domain.dto.UserAccountsDTO;
import com.challenge.users.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {

    List<User> list(Optional<String> q);
    User save(User user);
    UserAccountsDTO findById(Long userId);

}
