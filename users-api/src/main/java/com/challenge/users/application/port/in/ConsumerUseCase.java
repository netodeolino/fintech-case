package com.challenge.users.application.port.in;

import com.challenge.users.domain.dto.ConsumerDTO;

public interface ConsumerUseCase {

    ConsumerDTO save(ConsumerDTO consumerDTO);
    ConsumerDTO findByUserId(long userId);

}
