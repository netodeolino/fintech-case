package com.challenge.transaction.adapter.in.stream;

import com.challenge.transaction.application.exception.UnprocessableException;
import com.challenge.transaction.application.port.in.TransactionUseCase;
import com.challenge.transaction.domain.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransactionConsumer {

    private final Logger log = LoggerFactory.getLogger(TransactionConsumer.class);

    private final TransactionUseCase transactionUseCase;

    @Autowired
    public TransactionConsumer(TransactionUseCase transactionUseCase) {
        this.transactionUseCase = transactionUseCase;
    }

    @KafkaListener(topics = "${topics.validate-transaction}", groupId = "users-group-id")
    public void consumeTransactionMessage(String message) {
        log.info("Consumed message: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            transactionUseCase.validateMessage(objectMapper.readValue(message, TransactionDTO.class));
        } catch (IOException e) {
            throw new UnprocessableException("Error to consume message: " + message);
        }
    }

}
