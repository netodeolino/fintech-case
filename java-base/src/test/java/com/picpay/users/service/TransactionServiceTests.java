package com.picpay.users.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picpay.users.model.Transaction;
import com.picpay.users.model.User;
import com.picpay.users.model.dto.TransactionDTO;
import com.picpay.users.repository.TransactionRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTests {

	private Transaction transaction;

	@MockBean
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionService transactionService;

	@Before
	public void setup() throws JsonProcessingException, URISyntaxException {
		User userPayee = new User();
		userPayee.setId(1L);

		User userPayer = new User();
		userPayer.setId(2L);

		this.transaction = new Transaction(1L, userPayee, userPayer, 100.0, new Date());

		when(this.transactionRepository.findById(anyLong())).thenReturn(Optional.of(this.transaction));
	}

	@Test
	public void testFindById() {
		TransactionDTO transactionDTO = this.transactionService.findById(1L);

		assertThat(transactionDTO.getId()).isEqualTo(this.transaction.getId());
	}
}
