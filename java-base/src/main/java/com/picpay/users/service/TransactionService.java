package com.picpay.users.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.picpay.users.exception.NotFoundException;
import com.picpay.users.exception.UnauthorizedException;
import com.picpay.users.exception.UnprocessableException;
import com.picpay.users.model.Transaction;
import com.picpay.users.model.dto.TransactionDTO;
import com.picpay.users.repository.TransactionRepository;
import com.picpay.users.util.Constants;

import net.minidev.json.JSONObject;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Value("${TRAN_URL:http://127.0.0.1:8001}")
	private String TRAN_URL;

	private RestTemplate restTemplate;

	public TransactionService() {
		this.restTemplate = new RestTemplate();
	}

	public TransactionDTO transaction(TransactionDTO transactionDTO) {
		HttpHeaders headersRequest = new HttpHeaders();
		headersRequest.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		JSONObject transJson = new JSONObject();
		transJson.put("payee_id", transactionDTO.getPayeeId());
		transJson.put("payer_id", transactionDTO.getPayerId());
		transJson.put("value", transactionDTO.getValue());

		HttpEntity<String> httpEntity = new HttpEntity<>(transJson.toJSONString(), headersRequest);

		try {
			this.restTemplate.exchange(this.TRAN_URL + "/transactions/validate", HttpMethod.POST, httpEntity, String.class);

			transactionDTO.setTransactionDate(new Date());
			Transaction transactionSaved = this.transactionRepository.save(Transaction.mapFromDTO(transactionDTO));

			return Transaction.mapFromEntity(transactionSaved);
		} catch (HttpStatusCodeException e) {
			if (HttpStatus.UNPROCESSABLE_ENTITY == e.getStatusCode()) {
				throw new UnprocessableException(e.getMessage());
			}
			if (HttpStatus.UNAUTHORIZED == e.getStatusCode()) {
				throw new UnauthorizedException(e.getMessage());
			}

			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

	public TransactionDTO findById(Long transaction_id) {
		Transaction transaction = this.transactionRepository.findById(transaction_id).map(tran -> tran)
				.orElseThrow(() -> new NotFoundException(Constants.TRANSACTION_NOT_FOUND));

		return new TransactionDTO(transaction.getId(), transaction.getPayee().getId(), transaction.getPayer().getId(),
				transaction.getValue(), transaction.getTransactionDate());
	}

}
