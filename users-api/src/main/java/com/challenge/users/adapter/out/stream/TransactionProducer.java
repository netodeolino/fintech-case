package com.challenge.users.adapter.out.stream;

import com.challenge.users.application.exception.UnprocessableException;
import com.challenge.users.application.port.out.TransactionStreamPort;
import com.challenge.users.config.KafkaProperties;
import com.challenge.users.domain.dto.TransactionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer implements TransactionStreamPort {

    private final Logger log = LoggerFactory.getLogger(TransactionProducer.class);

    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public TransactionProducer(KafkaProperties kafkaProperties, KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransactionValidateMessage(TransactionDTO transactionDTO) {
        String topic = kafkaProperties.getValidateTransaction();
        log.info("Sending message to topic: {} with value: {}", topic, transactionDTO.toString());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(transactionDTO));
        } catch (JsonProcessingException e) {
            throw new UnprocessableException("Error to send message: " + transactionDTO);
        }
    }

}
